package cf.yellowstrawberry.ystweber.sender;

import java.util.HashMap;
import java.util.Map;

public class Types {
    static Map<Integer, String> codeDescriptions = new HashMap<>() {
        {
            put(100, "Continue");
            put(101, "Switching Protocols");
            put(102, "Processing");
            put(103, "Early Hints");
            put(200, "OK");
            put(201, "Created");
            put(202, "Accepted");
            put(203, "Non-Authoritative Information");
            put(204, "No Content");
            put(205, "Reset Content");
            put(206, "Partial Content");
            put(207, "Multi-Status");
            put(208, "Already Reported");
            put(226, "IM Used");
            put(300, "Multiple Choices");
            put(301, "Moved Permanently");
            put(302, "Found (Moved Temporarily)");
            put(303, "See Other");
            put(304, "Not Modified");
            put(305, "Use Proxy");
            put(306, "Switch Proxy");
            put(307, "Temporary Redirect");
            put(308, "Permanent Redirect");
            put(400, "Bad Request");
            put(401, "Unauthorized");
            put(402, "Payment Required");
            put(403, "Forbidden");
            put(404, "Not Found");
            put(405, "Method Not Allowed");
            put(406, "Not Acceptable");
            put(407, "Proxy Authentication");
            put(408, "Request Timeout");
            put(409, "Conflict");
            put(410, "Gone");
            put(411, "Length Required");
            put(412, "Precondition Failed");
            put(413, "Payload Too Large");
            put(414, "URI Too Long");
            put(415, "Unsupported Media Type");
            put(416, "Range Not Satisfiable");
            put(417, "Expectation Failed");
            put(421, "Misdirected Request");
            put(422, "Unprocessable Entity");
            put(423, "Locked");
            put(424, "Failed Dependency");
            put(425, "Too Early");
            put(426, "Upgrade Required");
            put(428, "Precondition Required");
            put(429, "Too Many Requests");
            put(431, "Request Header Fields Too Large");
            put(451, "Unavailable For Legal Reasons");
            put(500, "International Server Error");
            put(501, "Not Implemented");
            put(502, "Bad Gateway");
            put(503, "Service Unavailable");
            put(504, "Gateway Timeout");
            put(505, "HTTP Version not supported");
            put(506, "Variant Also Negotiates");
            put(507, "Insufficient Storage");
            put(508, "Loop Detected");
            put(509, "Bandwidth Limit Exceeded");
            put(510, "Not Extended");
            put(511, "Network Authentication Required");
        }
    };

    //Source: https://wiki.selfhtml.org/wiki/MIME-Type/%C3%9Cbersicht
    static Map<String, String> media_types = new HashMap<>() {
        {
            put("dwg", "application/acad");
            put("asd", "application/astound");
            put("asn", "application/astound");
            put("tsp", "application/dsptype");
            put("dx", "application/dxf");
            put("reg", "application/force-download");
            put("spl", "application/funturesplash");
            put("gz", "application/gzip");
//            put("js", "application/javascript"); Moving to text/javascript
            put("json", "application/json");
            put("ptlk", "application/listenup");
            put("hqx", "application/mac-binhex40");
            put("mbd", "application/mbedlet");
            put("mif", "application/mif");
            put("xls", "application/msexcel");
            put("xla", "application/msexcel");
            put("hlp", "application/mshelp");
            put("chm", "application/mshelp");
            put("ppt", "application/mspowerpoint");
            put("ppz", "application/mspowerpoint");
            put("pps", "application/mspowerpoint");
            put("pot", "application/mspowerpoint");
            put("doc", "application/msword");
            put("dot", "application/msword");
            put("bin", "application/octet-stream");
            put("file", "application/octet-stream");
            put("com", "application/octet-stream");
            put("class", "application/octet-stream");
            put("ini", "application/octet-stream");
            put("oda", "application/oda");
            put("pdf", "application/pdf");
            put("ai", "application/postscript");
            put("eps", "application/postscript");
            put("ps", "application/postscript");
            put("rtc", "application/rtc");
//            put("rtf", "application/rtf"); Moving to text/rtf
            put("smp", "application/studiom");
            put("tbk", "application/toolbook");
            put("odc", "application/vnd.oasis.opendocument.chart");
            put("odf", "application/vnd.oasis.opendocument.formula");
            put("odg", "application/vnd.oasis.opendocument.graphics");
            put("odi", "application/vnd.oasis.opendocument.image");
            put("odp", "application/vnd.oasis.opendocument.presentation");
            put("ods", "application/vnd.oasis.opendocument.spreadsheet");
            put("odt", "application/vnd.oasis.opendocument.text");
            put("odm", "application/vnd.oasis.opendocument.text-master");
            put("vmd", "application/vocaltec-media-desc");
            put("vmf", "application/vocaltec-media-file");
            put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//            put("htm", "application/xhtml+xml"); Moving to text/html
//            put("html", "application/xhtml+xml"); Moving to text/html
//            put("shtml", "application/xhtml+xml"); Moving to text/html
//            put("xhtml", "application/xhtml+xml"); Moving to text/html
//            put("xml", "application/xml"); Moving to text/xml
            put("bcpio", "application/x-bcpio");
            put("z", "application/x-compress");
            put("cpio", "application/x-cpio");
            put("csh", "application/x-csh");
            put("dcr", "application/x-director");
            put("dir", "application/x-director");
            put("dxr", "application/x-director");
            put("dvi", "application/x-dvi");
            put("evy", "application/x-envoy");
            put("gtar", "application/x-gtar");
            put("hdf", "application/x-hdf");
            put("php", "application/x-httpd-php");
            put("phtml", "application/x-httpd-php");
            put("latex", "application/x-latex");
            put("nc", "application/x-netcdf");
            put("cdf", "application/x-netcdf");
            put("nsc", "application/x-nschat");
            put("sh", "application/x-sh");
            put("shar", "application/x-shar");
            put("swf", "application/x-shockwave-flash");
            put("cab", "application/x-shockwave-flash");
            put("spr", "application/x-sprite");
            put("sprite", "application/x-sprite");
            put("sit", "application/x-stuffit");
            put("sca", "application/x-supercard");
            put("sv4cpio", "application/x-sv4cpio");
            put("sv4crc", "application/x-sv4crc");
            put("tar", "application/x-tar");
            put("tcl", "application/x-tcl");
            put("tex", "application/x-tex");
            put("texinfo", "application/x-texinfo");
            put("texi", "application/x-texinfo");
            put("t", "application/x-troff");
            put("tr", "application/x-troff");
            put("roff", "application/x-troff");
            put("ustar", "application/x-ustar");
            put("src", "application/x-wais-source");
            put("zip", "application/zip");
            put("au", "audio/basic");
            put("snd", "audio/basic");
            put("es", "audio/echospeech");
            put("mp3", "audio/mpeg");
//            put("mp4", "audio/mp4"); Moving to video/mp4
            put("ogg", "audio/ogg");
            put("tsi", "audio/tsplayer");
            put("vox", "audio/voxware");
            put("wav", "audio/wav");
            put("aif", "audio/x-aiff");
            put("aiff", "audio/x-aiff");
            put("aifc", "audio/x-aiff");
            put("dus", "audio/x-dspeech");
            put("cht", "audio/x-dspeech");
            put("mid", "audio/x-midi");
            put("midi", "audio/x-midi");
            put("mp2", "audio/x-mpeg");
            put("ram", "audio/x-pn-realaudio");
            put("ra", "audio/x-pn-realaudio");
            put("rpm", "audio/x-pn-realaudio-plugin");
            put("stream", "audio/x-qt-stream");
            put("dwf", "drawing/x-dwf");
            put("bmp", "image/bmp");
            put("cod","image/cis-cod");
            put("ras","image/cmu-raster");
            put("fif","image/fif");
            put("gif","image/gif");
            put("ief","image/ief");
            put("jpeg","image/jpeg");
            put("jpg","image/jpeg");
            put("jpe","image/jpeg");
            put("png","image/png");
            put("svg","image/svg+xml");
            put("tiff","image/tiff");
            put("tif","image/tiff");
            put("mcf","image/vasa");
            put("wbmp","image/vnd.wap.wbmp");
            put("fh4","image/x-freehand");
            put("fh5","image/x-freehand");
            put("fhc","image/x-freehand");
            put("ico","image/x-icon");
            put("prm","image/x-portable-anymap");
            put("pbm","image/x-portable-bitmap");
            //으억 살려줘
            put("pgm","image/x-portable-graymap");
            put("ppm","image/x-pixmap");
            put("rgb","image/x-rgb");
            put("xwd","image/x-windowdump");
            put("xbm","image/x-xbitmap");
            put("xpm","image/x-xpixmap");
            put("wrl","model/vrml");
            put("ics","text/calendar");
            put("csv","text/csv");
            put("css","text/css");
            put("html","text/html");
            put("htm","text/html");
            put("shtml","text/html");
            put("xhtml","text/html");
            put("js","text/javascript");
            put("md","text/markdown");
            put("markdown","text/markdown");
            put("txt","text/plain");
            put("rtx","text/richtext");
            put("rtf","text/rtf");
            put("tsv","text/tab-separated-values");
            put("mpeg","video/mpeg");
            put("mpg","video/mpeg");
            put("mpe","video/mpeg");
            put("mp4","video/mp4");
            put("ogv","video/ogg");
            put("qt","video/quicktime");
            put("mov","video/quicktime");
            put("viv","video/vnd.vivo");
            put("vivo","video/vnd.vivo");
            put("webm","video/webm");
            put("avi","video/x-msvideo");
            put("movie","video/x-sgi-movie");
            put("3gp","video/3gpp");
            put("xml", "text/xml");
            //노랑딸기 개발자.. 여기서 잠들다..
        }

        @Override
        public String get(Object key) {
            return super.getOrDefault(key, "application/octet-stream");
        }
    };
}
