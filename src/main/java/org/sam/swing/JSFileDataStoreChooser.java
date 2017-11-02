
package org.sam.swing;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


/**
 * A file chooser dialog to get user choices for data stores.
 * <p>
 * Examples of use:
 * <pre>{@code
 * // prompt the user for a shapefile
 * File file = JFileDataStoreChooser.showOpenFile("shp", parentFrame);
 * if (file != null) {
 *    ...
 * }
 *
 * // prompt the user for a given data format
 *
 * }</pre>
 *
 * @author Jody Garnett
 * @since 2.6
 *
 *
 *
 * @source $URL$
 * @version $Id$
 */
public class JSFileDataStoreChooser extends JFileChooser {
    private static final long serialVersionUID = -7482109609487216939L;

    /**
     * Create a dialog that filters for files with the specified extension.
     *
     * @param extension the file extension, with or without the leading '.'
     */
    public JSFileDataStoreChooser(final String extension) {
        this(new String[]{extension});
    }

    static Map<String, String> associations( List<String> extensions ){
        Map<String, String> fileAssociations = new TreeMap<String, String>();

        for (String extension : extensions) {
            String ext = extension.toLowerCase().trim();
            if (!ext.startsWith(".")) {
                ext = "." + ext;
            }

            if (".csv".equals(ext)) {
                fileAssociations.put(ext, "Comma-delimited files (*.csv)");
            } else if (ext.startsWith(".tif")) {
                fileAssociations.put(ext, "GeoTIFF files (*.tif; *.tiff)");
            } else {
                fileAssociations.put(ext, ext.toUpperCase().substring(1) + "files (*" + ext + ")");
            }
        }
        return fileAssociations;
    }
    
    /**
     * Create a dialog that filters for files with the specified extensions.
     *
     * @param extensions the file extensions, with or without the leading '.'
     */
    public JSFileDataStoreChooser(final List<String> extensions) {
        this( associations( extensions ));
    }
    
    /**
     * Create a dialog that filters for files with the specified extensions.
     *
     * @param extensions the file extensions, with or without the leading '.'
     */
    public JSFileDataStoreChooser(final String[] extensions) {
        this( associations( Arrays.asList(extensions)));
    }

    /**
     * Creates a dialog based on the given file associations.
     *
     * <pre><code>
     * Map<String, String> assoc = new HashMap<String, String>();
     * assoc.put(".foo", "Foo data files (*.foo)");
     * assoc.put(".bar", "Bar data files (*.bar)");
     * JFileDataStoreChooser chooser = new JFileDataStoreChooser(assoc);
     * </code></pre>
     *
     * @param fileAssociations a {@code Map} where keys are extensions (with or
     *        wirhout the leading dot) and values are descriptions.
     */
    public JSFileDataStoreChooser(final Map<String, String> fileAssociations) {
        init( fileAssociations );
    }

    /**
     * Helper method for constructors that creates file filters.
     *
     * @param fileAssociations a {@code Map} where keys are extensions (with or
     *        wirhout the leading dot) and values are descriptions.
     */
    private void init(final Map<String, String> fileAssociations) {

    	this.setAcceptAllFileFilterUsed(false);
    	
        for (final String ext : fileAssociations.keySet()) {
            addChoosableFileFilter(new FileFilter() {

                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }

                    for (String ext : fileAssociations.keySet()) {
                        if (f.getPath().endsWith(ext) ||
                                f.getPath().endsWith(ext.toUpperCase())) {
                            return true;
                        }
                    }

                    return false;
                }

                @Override
                public String getDescription() {
                    return fileAssociations.get(ext);
                }
            });
        }
    }


    /**
     * Show a file open dialog that filters for files with the given extension.
     *
     * @param extension file extension, with or without leading '.'
     * @param parent parent GUI component (may be {@code null})
     *
     * @return the selected file or null if the user cancelled the selection
     * @throws java.awt.HeadlessException if run in an unsupported environment
     */
    public static File showOpenFile(String extension, Component parent)
            throws HeadlessException {
        return showOpenFile(extension, null, parent);
    }

    /**
     * Show a file open dialog that filters for files with the given extension.
     *
     * @param extension file extension, with or without leading '.'
     * @param initialDir initial directory to display; if {@code null} the initial directory
     *        will be the user's default directory
     * @param parent parent GUI component (may be {@code null})
     *
     * @return the selected file or null if the user cancelled the selection
     * @throws java.awt.HeadlessException if run in an unsupported environment
     */
    public static File showOpenFile(String extension, File initialDir, Component parent)
            throws HeadlessException {
        JSFileDataStoreChooser dialog = new JSFileDataStoreChooser(extension);
        if (initialDir != null) {
            if (initialDir.isDirectory()) {
                dialog.setCurrentDirectory(initialDir);
            } else {
                dialog.setCurrentDirectory(initialDir.getParentFile());
            }
        }
        
        if (dialog.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        
        return null;
    }

    /**
     * Show a file open dialog that filters for files with the given extensions.
     *
     * @param extensions array of file extension, with or without leading '.'
     * @param parent parent GUI component (may be null)
     *
     * @return the selected file or null if the user cancelled the selection
     * @throws java.awt.HeadlessException if run in an unsupported environment
     */
    public static File showOpenFile(String[] extensions, Component parent)
            throws HeadlessException {
        return showOpenFile(extensions, null, parent);
    }

    /**
     * Show a file open dialog that filters for files with the given extensions.
     *
     * @param extensions array of file extension, with or without leading '.'
     * @param initialDir initial directory to display; if {@code null} the initial directory
     *        will be the user's default directory
     * @param parent parent GUI component (may be null)
     *
     * @return the selected file or null if the user cancelled the selection
     * @throws java.awt.HeadlessException if run in an unsupported environment
     */
    public static File showOpenFile(String[] extensions, File initialDir, Component parent)
            throws HeadlessException {

        JSFileDataStoreChooser dialog = new JSFileDataStoreChooser(extensions);
        if (initialDir != null) {
            if (initialDir.isDirectory()) {
                dialog.setCurrentDirectory(initialDir);
            } else {
                dialog.setCurrentDirectory(initialDir.getParentFile());
            }
        }

        if (dialog.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }

        return null;
    }
    /**
     * Consider the provided file as a candidate for a new filename. 
     * A number will be appended to the filename if there is a
     * conflict.
     * 
     * @param file the candidate file name
     */
    public void setSaveFile(File file) {
        String path = file.getAbsolutePath();
        int split = path.lastIndexOf('.');
        String base;
        String extension;
        if( split == -1 ){
            base = path;
            extension = "";
        }
        else {
            base = path.substring(0, split);
            extension = path.substring(split);
        }
        File saveFile = new File( path );
        int number = 0;
        while( saveFile.exists() ){
            saveFile = new File( base+(number++)+extension );            
        }
        setSelectedFile( saveFile );        
    }
}
