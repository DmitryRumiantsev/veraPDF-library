package org.verapdf.model;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.verapdf.model.factory.cos.PBFactory;
import org.verapdf.model.coslayer.CosDict;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Evgeniy Muravitskiy on 5/13/2015.
 * <p>
 *     Current class is entry point to model implementation. Implements Singleton pattern.
 * </p>
 */
public final class ModelLoader {

    private ModelLoader(){}

    /**
     * Method return root object of model implementation from pdf box model together with the hierarchy.
     * @param path path to PDF file
     * @return root object representing by {@link org.verapdf.model.coslayer.CosDict}
     * @throws FileNotFoundException
     */
    public static CosDict getCatalog(String path) throws FileNotFoundException {
        final File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Current file '" + path + "' not exists.");
        }

        CosDict catalog = null;
        try (PDDocument document = PDDocument.load(file)) {
            COSDictionary pdfBoxCatalog = (COSDictionary) document.getDocument().getCatalog().getObject();
            catalog = (CosDict) PBFactory.generateCosObject(pdfBoxCatalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catalog;
    }
}