package test.aux;

public class RandomBytesGenerator {
    public static byte[] getRandomBytes(int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++ i)
            bytes[i] = (byte) (Math.random() * 255);
        return bytes;
    }
}
