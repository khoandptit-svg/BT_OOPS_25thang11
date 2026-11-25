import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class ConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

class HoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

class HocBong implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, 3000000L);
    }
}

abstract class SinhVien {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected MienGiam chinhSach;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
    }

    public String getMaHe() {
        return maHe;
    }

    public void setChinhSach(MienGiam chinhSach) {
        this.chinhSach = chinhSach;
    }

    public abstract long hocPhi();

    public long soTienGiam() {
        if (chinhSach == null) return 0;
        return chinhSach.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - soTienGiam();
    }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi() + " " + soTienGiam() + " " + phaiNop();
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

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        Map<String, SinhVien> mapSV = new LinkedHashMap<>();
        List<SinhVien> danhSach = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String he = sc.next();
            String ma = sc.next();
            int tc = sc.nextInt();
            SinhVien sv = null;
            switch (he) {
                case "CQ":
                    sv = new SinhVienCQ(ma, tc);
                    break;
                case "CLC":
                    sv = new SinhVienCLC(ma, tc);
                    break;
                case "LT":
                    sv = new SinhVienLT(ma, tc);
                    break;
            }
            if (sv != null) {
                mapSV.put(ma, sv);
                danhSach.add(sv);
            }
        }

        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            String maSV = sc.next();
            String maCS = sc.next();
            SinhVien sv = mapSV.get(maSV);
            if (sv == null) {
                System.out.println("NOT FOUND " + maSV);
            } else {
                switch (maCS) {
                    case "CS":
                        sv.setChinhSach(new ConChinhSach());
                        break;
                    case "HN":
                        sv.setChinhSach(new HoNgheo());
                        break;
                    case "HB":
                        sv.setChinhSach(new HocBong());
                        break;
                }
            }
        }

        for (SinhVien sv : danhSach) {
            System.out.println(sv);
        }

        String[] cacHe = {"CQ", "CLC", "LT"};
        for (String he : cacHe) {
            int count = 0;
            long tongPhaiNop = 0;
            for (SinhVien sv : danhSach) {
                if (sv.getMaHe().equals(he)) {
                    count++;
                    tongPhaiNop += sv.phaiNop();
                }
            }
            if (count > 0) {
                System.out.println(he + " " + count + " " + tongPhaiNop);
            }
        }
    }
}