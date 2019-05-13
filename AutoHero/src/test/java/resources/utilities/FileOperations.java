package resources.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperations {

	FileWriter fwObject = null;

	/**
	 * This method initializes the file operation object.
	 * 
	 * @param filePath : Opens the file handle for operations.
	 * @return Nothing.
	 */
	public FileOperations(String filePath) {
		try {
			fwObject = new FileWriter(filePath);
		} catch (IOException io) {
			System.out.println("Oops! Exception while writing to file!" + io.getMessage());
		}
	}

	/**
	 * This method contains logic to write contents to the file.
	 * 
	 * @param dataToBeWritten : Contents to be written in the file.
	 * @return Nothing.
	 */
	public void writeToFile(String dataToBeWritten) {
		try {
			fwObject.write(dataToBeWritten);
		} catch (IOException io) {
			System.out.println("Oops! Exception while writing to file!" + io.getMessage());
		}
	}

	/**
	 * This method takes care of closing all file handles
	 * 
	 * @param None.
	 * @return Nothing.
	 */
	public void closeFileHandle() {
		try {
			if (fwObject != null)
				fwObject.close();
		} catch (IOException io) {
			System.out.println("Oops! Exception while writing to file!" + io.getMessage());
		}
	}

	/**
	 * This method contains logic to create a new directory if it does not already
	 * exists.
	 * 
	 * @param directory : Full path of directory.
	 * @return Nothing.
	 */
	public static void createDirectory(String directory) {
		try {
			File theDir = new File(directory);
			if (!theDir.exists()) {
				boolean result = theDir.mkdir();
				if (result) {
					System.out.println("successfully created directory" + directory);
				}
			}
		} catch (Exception e) {
			System.out.println("Oops! Exception while creating directory : " + directory + " " + e.getMessage());
		}
	}

	/**
	 * This method contains logic to check if directory already exists.
	 * 
	 * @param directory : Full path of directory.
	 * @return Nothing.
	 */
	public static Boolean checkIfDirExists(String directoryName) {
		try {
			File directory = new File(directoryName);

			if (directory.exists())
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("Oops! Exception while writing to file!" + e.getMessage());
		}
		return true;
	}
}
