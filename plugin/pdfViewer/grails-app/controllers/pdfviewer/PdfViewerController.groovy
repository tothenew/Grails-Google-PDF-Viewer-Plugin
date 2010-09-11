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
        String absoluteUrl = "http://qa3.intelligrape.net:12345/samplePdfViewer/"
        String url = googleDocUrl + absoluteUrl + "pdfViewer/viewPdf/" + googleDocInstance.uniqueId
        redirect(url: url)
    }

    def viewPdf = {        
        GoogleDocPdf googleDocPdf = GoogleDocPdf.findByUniqueId(params.uniqueId)

        File file = new File(googleDocPdf.path)
            String name = googleDocPdf.path.replaceAll(" ", "_")
            response.setHeader("Content-disposition", "attachment; filename="+name)
            response.setContentType("pdf")
            response.setContentLength(file.size().toInteger());
            OutputStream out = response.getOutputStream();
            out.write(file.readBytes());
            out.flush()
            out.close();

        if(googleDocPdf){
            googleDocPdf.delete(flush:true)
        }
    }
}
