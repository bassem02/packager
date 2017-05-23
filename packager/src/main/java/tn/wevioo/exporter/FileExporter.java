package tn.wevioo.exporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nordnet.architecture.exceptions.implicit.IllegalParameterException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.exceptions.PackagerException;

/**
 * The class FileExporter provides all methods required to implement an exporter
 * providing result under a file format.
 */
public abstract class FileExporter implements PackagerExporter {

	/**
	 * The name of the final file. It will be written into the workspace
	 * directory.
	 */
	protected String finalName;

	/**
	 * Complete path to the directory in which files will be created.
	 */
	protected String workspace;

	/**
	 * Number of appended elements at which files have to be flushed.This will
	 * allow better performance.
	 */
	protected Integer flushFrequency;

	/**
	 * Contains all temporary file names. This attribute is supposed to ensure
	 * the order in which the files are created.
	 */
	protected List<String> temporaryFileNames = new ArrayList<String>();

	/**
	 * Size of providerProductIds used to search for product instances. This
	 * will allow better performance.
	 */
	protected Integer searchFrequency;

	public Integer getFlushFrequency() {
		return flushFrequency;
	}

	public void setFlushFrequency(Integer flushFrequency) {
		this.flushFrequency = flushFrequency;
	}

	public Integer getNbrAppendedEntities() {
		return nbrAppendedEntities;
	}

	public void setNbrAppendedEntities(Integer nbrAppendedEntities) {
		this.nbrAppendedEntities = nbrAppendedEntities;
	}

	/**
	 * Allows counting the number of entities which have been appended since the
	 * last flush. Is strongly related to attribute flushFrequency. Initial
	 * Value : 0.
	 */
	protected Integer nbrAppendedEntities = 0;

	/**
	 * The method getTemporaryFile allows providing a new temporary file,
	 * created into workspace folder and whose name is generated randomly, using
	 * UUID algorithm. Its extension will be ".tmp". If the file cannot be
	 * created, the method returns a PackagerException.
	 * 
	 * @return new temporary file buffer writer.
	 * @throws PackagerException
	 *             custom exception.
	 */
	protected BufferedWriter getTemporaryFile() throws PackagerException {
		String fileName = UUID.randomUUID().toString() + ".tmp";
		temporaryFileNames.add(fileName);
		return getFile(fileName);
	}

	/**
	 * The method getFile returns a buffered writer on a file created into
	 * workspace and whose file name is the one provided as parameters. If any
	 * exception occurs while creating the file, a PackagerException is thrown.
	 * 
	 * @param fileName
	 *            Complete file name (including extension if required). Cannot
	 *            be null or empty.
	 * @return
	 * @throws PackagerException
	 */
	private BufferedWriter getFile(String fileName) throws PackagerException {
		if (fileName == null || fileName.trim().length() == 0) {
			throw new NullException(NullCases.NULL_EMPTY, "file name");
		}
		try {
			if ((workspace.endsWith("/")) || (workspace.endsWith("\\"))) {
				if (!workspace.endsWith(System.getProperty("file.separator"))) {
					workspace = workspace.substring(0, workspace.length() - 1);
					workspace += System.getProperty("file.separator");
				}
			} else {
				workspace += System.getProperty("file.separator");
			}

			return new BufferedWriter(new FileWriter(workspace + fileName));
		} catch (Exception e) {
			throw new PackagerException(new ErrorCode("0.1.1.2.6"), new Object[] { fileName }, e);
		}
	}

	/**
	 * The method getFinalFile returns a buffered writer on the final result
	 * file. This file will be created into workspace. If an exception occurs
	 * while creating the file, the method throws a PackagerException.
	 * 
	 * @return returns a buffered writer on the final result file.
	 * @throws PackagerException
	 *             custom exception.
	 */
	protected BufferedWriter getFinalFile() throws PackagerException {
		return getFile(finalName);
	}

	/**
	 * The method flush allows flushing all the required resources. If an error
	 * occurs when flushing the different resources, the method throws a
	 * PackagerException.
	 * 
	 * @throws PackagerException
	 *             custom exception.
	 */
	protected abstract void flush() throws PackagerException;

	public String getWorkspace() {
		return workspace;
	}

	public String getFinalName() {
		return finalName;
	}

	public void setFinalName(String finalName) {
		this.finalName = finalName;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public void setSearchFrequency(Integer searchFrequency) {
		if (!(searchFrequency > 0)) {
			throw new IllegalParameterException(new ErrorCode("0.2.1.1.21"), new Object[] { "searchFrequency", 0 });
		}
		this.searchFrequency = searchFrequency;
	}

}
