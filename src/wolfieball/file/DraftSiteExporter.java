/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.file;

/**
 *
 * @author shilinlu
 */
public class DraftSiteExporter {
    // THESE ARE THE DIRECTORIES WHERE OUR BASE SCHEDULE
    // FILE IS AND WHERE OUR COURSE SITES WILL BE EXPORTED TO
    String baseDir;
    String sitesDir;

    // WE'LL USE THIS VARIABLE TO KEEP TRACK OF EXPORTING PROGRESS
    double perc = 0;
    int pageIndex;

    public DraftSiteExporter(String initBaseDir, String initSitesDir) {
        baseDir = initBaseDir;
        sitesDir = initSitesDir;
    }


}
