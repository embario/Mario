/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author mbarrenecheajr
 */
public class XMLfilter extends FileFilter{

    @Override
    public boolean accept(File f){

        if(f.isDirectory() == true)
            return true;
        
        String filename = f.getName().toLowerCase();
        return filename.endsWith(".xml");
    }

    @Override
    public String getDescription() {
        return "XML Files (*.xml)";
    }

}
