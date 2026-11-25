import java.util.Scanner;

class SinhVien {
    private String maSV;
    private String hoTen;
    private String ngaySinh;

    public SinhVien(String maSV, String hoTen, String ngaySinh) {
        this.maSV = maSV.trim();
        this.hoTen = chuanHoaHoTen(hoTen);
        this.ngaySinh = chuanHoaNgaySinh(ngaySinh);
    }

    private String chuanHoaHoTen(String ten) {
        String[] words = ten.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                  .append(w.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    private String chuanHoaNgaySinh(String ns) {
        String[] parts = ns.trim().split("/");
        String d = parts[0].length() == 1 ? "0" + parts[0] : parts[0];
        String m = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
        String y = parts[2];
        return d + "/" + m + "/" + y;
    }

    @Override
    public String toString() {
        return maSV + " " + hoTen + " " + ngaySinh;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        int n = Integer.parseInt(sc.nextLine().trim());
        SinhVien[] ds = new SinhVien[n];
        for (int i = 0; i < n; i++) {
            String ma = sc.nextLine();
            String ten = sc.nextLine();
            String ns = sc.nextLine();
            ds[i] = new SinhVien(ma, ten, ns);
        }
        for (SinhVien sv : ds) {
            System.out.println(sv);
        }
    }
}