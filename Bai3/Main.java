import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

abstract class SinhVien implements Comparable<SinhVien> {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
    }

    public abstract long hocPhi();

    @Override
    public int compareTo(SinhVien other) {
        int cmp = Long.compare(other.hocPhi(), this.hocPhi());
        if (cmp != 0) return cmp;
        return this.maSV.compareTo(other.maSV);
    }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi();
    }
}

class SinhVienCQ extends SinhVien {
    public SinhVienCQ(String maSV, int soTinChi) {
        super(maSV, "CQ", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class SinhVienCLC extends SinhVien {
    public SinhVienCLC(String maSV, int soTinChi) {
        super(maSV, "CLC", soTinChi);
    }

    @Override
    public long hocPhi() {
        return (long) (soTinChi * DON_GIA * 1.5);
    }
}

class SinhVienLT extends SinhVien {
    public SinhVienLT(String maSV, int soTinChi) {
        super(maSV, "LT", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + 2000000L;
    }
}

class QuanLyHocPhi {
    private List<SinhVien> danhSach = new ArrayList<>();
    private Set<String> daTonTai = new HashSet<>();

    public void themSinhVien(String maHe, String maSV, String strTinChi) throws DuLieuKhongHopLeException {
        if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT")) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        int soTinChi;
        try {
            soTinChi = Integer.parseInt(strTinChi);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        if (soTinChi < 1 || soTinChi > 30) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        if (daTonTai.contains(maSV)) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        daTonTai.add(maSV);
        switch (maHe) {
            case "CQ":
                danhSach.add(new SinhVienCQ(maSV, soTinChi));
                break;
            case "CLC":
                danhSach.add(new SinhVienCLC(maSV, soTinChi));
                break;
            case "LT":
                danhSach.add(new SinhVienLT(maSV, soTinChi));
                break;
        }
    }

    public void inKetQua() {
        Collections.sort(danhSach);
        long tongHocPhi = 0;
        for (SinhVien sv : danhSach) {
            System.out.println(sv);
            tongHocPhi += sv.hocPhi();
        }
        System.out.println("TONG " + tongHocPhi);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        QuanLyHocPhi ql = new QuanLyHocPhi();

        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            String strTinChi = sc.next();
            try {
                ql.themSinhVien(maHe, maSV, strTinChi);
            } catch (DuLieuKhongHopLeException e) {
                System.out.println(e.getMessage());
            }
        }
        ql.inKetQua();
    }
}