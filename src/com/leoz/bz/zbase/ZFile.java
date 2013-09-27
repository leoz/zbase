package com.leoz.bz.zbase;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import android.net.Uri;

public class ZFile implements Comparable<ZFile> {

	public static final String PARENT = "..";
	
	private File mSysFile = null;
	
	private boolean mRoot = false;

	public ZFile(String url) {
		this.mSysFile = new File(url);
	}

	private ZFile(File sysf) {
		if (sysf != null) {
			this.mSysFile = sysf;	
		}
	}
	
	public int compareTo(ZFile another) {
		if (another != null)
			return nullSafeFileComparator(this.mSysFile, another.mSysFile);
		else
	        return 1;
	}

	public ZFile[] listFiles() {
		
		ZFile[] ff = null;
		File[] files = null;
		
		if (this.isDirectory()) {
			files = this.mSysFile.listFiles();
			if (files != null) {
				ff = new ZFile[files.length];
				int i=0;
				for (File file : files) {
					ff [i] = new ZFile(file);
					i++;
				}
			}
		}
		return ff;
	}
	
	public ZFile[] listDir() {
		
		File[] files = null;
        int length = 0;
        File parent = null;
        
		if (this.isDirectory()) {
			files = this.mSysFile.listFiles();
        	if (files != null) {
                length = files.length;       		
        	}
        	parent = this.mSysFile.getParentFile();
        	if (parent != null) {
        		length++;
        	}
     	}
		
		ZFile[] ff = null;
		
		if (length > 0) {
			
	    	ff = new ZFile[length];
	    	
	    	if (files != null) {
				int i=0;
				for (File file : files) {
					ff [i] = new ZFile(file);
					i++;
				}
	    	}
	    	
        	if (parent != null) {
    	        ff[length - 1] = new ZFile (parent);
    	        ff[length - 1].mRoot = true;
        	}
	
	        Arrays.sort(ff);
		}
        
        return ff;
    }
	
	/////////////////////////////////////////
	/// File operations

	public boolean isFile() {
		return (mSysFile == null) ? false : mSysFile.isFile();
	}

	public boolean isDirectory() {
		return (mSysFile == null) ? false : mSysFile.isDirectory();
	}
	
	public boolean canExecute() {
		return (mSysFile == null) ? false : mSysFile.canExecute();
	}

	public boolean canRead() {
		return (mSysFile == null) ? false : mSysFile.canRead();
	}
	
	public String getName() {
		return (mRoot) ? PARENT : ((mSysFile == null) ? null : mSysFile.getName());
	}
	
	public String getPath() {
		return (mSysFile == null) ? null : mSysFile.getPath();
	}

	public String getAbsolutePath() {
		return (mSysFile == null) ? null : mSysFile.getAbsolutePath();
	}

	public String getCanonicalPath() throws IOException {
		return (mSysFile == null) ? null : mSysFile.getCanonicalPath();
	}

	public long length() {
		return (mSysFile == null) ? 0 : mSysFile.length();
	}

	public long lastModified() {
		return (mSysFile == null) ? 0 : mSysFile.lastModified();
	}

	public Uri getUri() {
		return Uri.fromFile(mSysFile);
	}

	public ZFile getParentFile() {
		return new ZFile ((mSysFile == null) ? null : mSysFile.getParentFile());
	}

	/// File operations
	/////////////////////////////////////////
	
	private static int nullSafeFileComparator(final File one, final File two) {
	    if (one == null ^ two == null) {
	        return (one == null) ? -1 : 1;
	    }
	
	    if (one == null && two == null) {
	        return 0;
	    }
	
	    return one.compareTo(two);
	}
}
