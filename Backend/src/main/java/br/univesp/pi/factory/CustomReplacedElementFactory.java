package br.univesp.pi.factory;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import com.lowagie.text.Image;

import java.util.Base64;

public class CustomReplacedElementFactory implements ReplacedElementFactory {
    private final ReplacedElementFactory superFactory;

    public CustomReplacedElementFactory(ReplacedElementFactory superFactory) {
        this.superFactory = superFactory;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
                                                 UserAgentCallback uac, int cssWidth, int cssHeight) {
        Element element = box.getElement();
        if (element == null) {
            return null;
        }

        String nodeName = element.getNodeName();
        if ("img".equals(nodeName)) {
            String src = element.getAttribute("src");
            try {
                if (src.startsWith("data:image")) {
                    // Processa imagens em base64
                    String base64Data = src.substring(src.indexOf(",") + 1);
                    byte[] imgBytes = Base64.getDecoder().decode(base64Data);
                    Image image = Image.getInstance(imgBytes);
                    ITextFSImage fsImage = new ITextFSImage(image);

                    if (cssWidth > 0 || cssHeight > 0) {
                        fsImage.scale(cssWidth, cssHeight);
                    }

                    return new ITextImageElement(fsImage);
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar imagem: " + e.getMessage());
            }
        }

        return superFactory.createReplacedElement(c, box, uac, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        superFactory.reset();
    }

    @Override
    public void remove(Element e) {
        superFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        superFactory.setFormSubmissionListener(listener);
    }
}
