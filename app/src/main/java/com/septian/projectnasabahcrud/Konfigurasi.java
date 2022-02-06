package com.septian.projectnasabahcrud;

public class Konfigurasi {

    public static final String URL_GET_ALL = "http://192.168.31.103/nasabah/tampilSemuaNsb.php";
    public static final String URL_GET_DETAIL = "http://192.168.31.103/nasabah/tampilNsb.php?id=";
    public static final String URL_GET_ADD = "http://192.168.31.103/nasabah/tambahNsb.php";
    public static final String URL_UPDATE = "http://192.168.31.103/nasabah/updateNsb.php?id=";
    public static final String URL_DELETE = "http://192.168.31.103/nasabah/hapusNsb.php?id=";

    // key add value JSON yang muncul di browser
    public static final String KEY_NSB_ID = "id";
    public static final String KEY_NSB_NAMA = "nama";
    public static final String KEY_NSB_REK ="no_rekening";
    public static final String KEY_NSB_TAB = "tabungan";
    public static final String KEY_NSB_SAL = "saldo";

    // flag JSON
    public static final String TAG_JASON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id";
    public static final String TAG_JSON_NAMA = "nama";
    public static final String TAG_JSON_REK = "no_rekening";
    public static final String TAG_JSON_TAB = "tabungan";
    public static final String TAG_JSON_SAL = "saldo";

    // variabel alias ID Pegawai
    public static final String NSB_ID = "nsb_id";


}
