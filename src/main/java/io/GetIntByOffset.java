package io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetIntByOffset {

  private final String FILE_NAME;

  public GetIntByOffset(String file_name) {
    FILE_NAME = file_name;
  }

  int getInt() {
    int result = -1;
    //    try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r"); FileChannel inChannel = raf.getChannel()) {
    try (DataInputStream in = new DataInputStream(new FileInputStream(FILE_NAME))) {
      long offset = in.readLong();
      System.out.print(offset + "\n");
      in.skip(offset);
      if (in.available() > 0) {
        result = in.readInt();
      }
    } catch (FileNotFoundException fe) {
      System.out.println("FileNotFoundException : " + fe);
    } catch (IOException ioe) {
      System.out.println("IOException : " + ioe);
    }
    return result;
  }

  int getIntNio(){
    int result = -1;
    try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(Paths.get(FILE_NAME))) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(8);
      seekableByteChannel.read(byteBuffer);// reads data from the FileChannel into the Buffer.
      // The int returned by the read() method tells how many bytes were witten into the Buffer.
      // If -1 is returned, the end-of-file is reached.
      long offset = byteBuffer.getLong(0);
      seekableByteChannel.position(offset);
      byteBuffer.rewind();
      seekableByteChannel.read(byteBuffer);
      result = byteBuffer.getInt(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static void main(String args[]) {
    GetIntByOffset go = new GetIntByOffset(
        "/Users/inas/Careem/Backend/src/main/resources/io/get_int_by_offset_file.dms");
    System.out.print(go.getInt());
    System.out.print(go.getIntNio());
  }
}
