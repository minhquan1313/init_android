public class Utils {
    public static void askPermission(String permission, int code) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
            requestPermissions(new String[] { permission }, code);
        }
    }
}
