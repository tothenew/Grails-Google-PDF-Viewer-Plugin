package pdfviewer

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class PdfViewerController {
    def index = { }
    def addDocumentUrl = {
        GoogleDocPdf googleDocInstance = new GoogleDocPdf()
        googleDocInstance.path = params.id
        googleDocInstance.uniqueId = UUID.randomUUID().toString()
        googleDocInstance.save(flush: true)

        String googleDocUrl = "http://docs.google.com/viewer?embedded=true&url="
        String url = googleDocUrl + "pdfViewer/viewPdf/" + googleDocInstance.uniqueId
        redirect(url: url)
    }

    def viewPdf = {
        GoogleDocPdf googleDocPdf = GoogleDocPdf.findByUniqueId(params.uniqueId)
        try{
        File file = new File(googleDocPdf.path)
            String name = googleDocPdf.path.replaceAll(" ", "_")
            response.setHeader("Content-disposition", "attachment; filename="+name)
            response.setContentType("pdf")
            response.setContentLength(file.size().toInteger());
            OutputStream out = response.getOutputStream();
            out.write(file.readBytes());
            out.flush()
            out.close();
        }catch(Exception e){
            e.printStackTrace()
        }

        if(googleDocPdf){
            googleDocPdf.delete(flush:true)
        }
    }
}
