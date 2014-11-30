package Operation;

import java.io.*;
import java.util.List;
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.FileLock;  

/**
 * Read a File content, and return as a String
 */
public class FileUtils {
	/**
	 * Read a File into buffer
	 * 
	 * @param buffer
	 *            buffer
	 * @param filePath
	 * @throws IOException
	 */
	public static void readToBuffer(StringBuffer buffer, String filePath)
			throws IOException {
		InputStream is = new FileInputStream(filePath);
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"utf-8"));

		line = reader.readLine();
		while (line != null) {
			buffer.append(line);
			buffer.append("\n");
			line = reader.readLine();
		}
		reader.close();
		is.close();
	}

	/**
	 * Read the content of file, into String
	 * 
	 * @param filePath
	 * @return Content of file(in String)
	 * @throws IOException
	 */
	public static String readFile(String filePath) throws IOException {
		StringBuffer sb = new StringBuffer();
		FileUtils.readToBuffer(sb, filePath);
		return sb.toString();
	}

	/**
	 * Write the content of a List into file
	 * 
	 * @param lst
	 * @param filePath
	 * @throws FileNotFoundException
	 *             IOException
	 */
	public static void writeFile(List<Object> lst, String filePath) throws IOException {
		FileOutputStream outStream = new FileOutputStream(filePath);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				outStream);
		objectOutputStream.writeObject(lst);
		outStream.close();
	}

	/**
	 * Write the content of a String into file
	 * 
	 * @param content
	 * @param filePath
	 */
	// public static void writeFile(String content, String filePath, Boolean
	// append) throws IOException
	// {
	// BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
	// append));
	// out.write(content);
	// out.close();
	// }

	public static void writeFile(String content, String filePath, Boolean append) {
		FileChannel channel = null;
		FileLock lock = null;
		RandomAccessFile raf = null;
		try {

			raf = new RandomAccessFile(filePath, "rw");
			if(append)
			{
				raf.seek(raf.length());
			}
			else
			{
				raf.setLength(0);
				raf.seek(0);
			}
			channel = raf.getChannel();

			do {
				lock = channel.tryLock();
			} while(null == lock);

			ByteBuffer bf = ByteBuffer.wrap(content.getBytes());
			channel.write(bf);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (lock != null) {
				try {
					lock.release();
					lock = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (channel != null) {
				try {
					channel.close();
					channel = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (raf != null) {
				try {
					raf.close();
					raf = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}