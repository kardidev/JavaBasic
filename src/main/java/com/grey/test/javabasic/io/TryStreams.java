package com.grey.test.javabasic.io;

import com.sun.deploy.util.StringUtils;

import java.io.*;

/**
 * Created by Grey on 22.03.2015.
 */
public class TryStreams {

    public static void testIt() throws IOException, ClassNotFoundException {

//        InputStream is;
//        OutputStream os;
//        Reader reader;
//        Writer writer;

        workingWithBytes();
        workingWithText();
        dataPipedStream();
        objectStream();

    }

    private static void workingWithBytes() {
        File file = null;
        try {
            file = File.createTempFile("grey", "java");

            // 1. Записать в файл строку в UTF-8
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            String testStr = "съешь ещё этих французских булочек";
            bos.write(testStr.getBytes("UTF-8"));
            bos.close();

            // 2. Считать ее из файла
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int res = 0;
            while((res = bis.read()) != -1 ) {
                buffer.write(res);
            }
            bis.close();

            // byte[] bytes = IOUtils.toByteArray(is);
            String strNew = new String(buffer.toByteArray(), "UTF-8");
            buffer.close();
            System.out.println(strNew);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (file != null) file.delete();
        }
    }

    private static void workingWithText() {
        File file = null;
        try {
            file = File.createTempFile("grey", "java");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("жизнь после джавы");
            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.println(reader.readLine());
            reader.close();


        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (file != null) file.delete();
        }
    }

    private static void dataPipedStream() throws IOException {

        byte[] byteArray = String.valueOf("я у мамы дурачек").getBytes("UTF-8");

        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream();
        out.connect(in);

        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(out));
        DataInputStream din = new DataInputStream(new BufferedInputStream(in));

        dout.writeInt(1234567);
        dout.writeLong(123456789);
        dout.writeUTF("я у мамы дурачек");
        dout.writeFloat(4.4f);
        dout.write(byteArray);
        dout.flush();

        System.out.println(din.readInt());
        System.out.println(din.readLong());
        System.out.println(din.readUTF());
        System.out.println(din.readFloat());
        byte[] arrrr = new byte[1000];
        din.read(arrrr);

        System.out.println(new String(arrrr, "UTF-8"));
    }

    private static void objectStream() throws IOException, ClassNotFoundException {

        File file = new File("g:/java/1.bin");
//        file.createNewFile();
//
//        ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
//
//        ClassToSerialize obj = new ClassToSerialize(777);
//        output.writeObject(obj);
//        output.close();



        ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
        ClassToSerialize objReturned = (ClassToSerialize)input.readObject();
        input.close();

        System.out.println(objReturned.a);
        System.out.println(objReturned.str);
    }

}

class BaseClassToSerialize implements Serializable {

    transient private ValueClass value = new ValueClass();

    public String str = "FUCKYUOooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo";

}

class ClassToSerialize extends BaseClassToSerialize {

    private static final long serialVersionUID = 1;

    public int a = 0;

    public ClassToSerialize() {}

    public ClassToSerialize(int a) {
        this.a = a;
    }



    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
        //stream.writeUTF(String.valueOf(a));
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        //a = Integer.valueOf(stream.readUTF());
    }
}

class ValueClass implements Serializable {

    public String str = "1222222222222222223333333333333333333333333333333333334444444444";

}