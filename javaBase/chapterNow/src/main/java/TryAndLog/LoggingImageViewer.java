package TryAndLog;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A modification of the image viewer program that logs various events.
 * 图像查看器程序的修改,记录各种事件。
 * @author 李阳
 *
 */
public class LoggingImageViewer
{
    public static final String LOGGER = "com.fuzekun.app";
    public static void main(String[] args)
    {
        /**
         * 下面这段代码确保所有的信息记录到应用程序特定文件中
         */
        if (System.getProperty("java.util.logging.config.class")==null
                && System.getProperty("java.util.logging.config.file")==null)
        {
            try {
                //开启所有级别的记录
                Logger.getLogger(LOGGER).setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("logs\\log.txt",0,LOG_ROTATION_COUNT);
                Logger.getLogger(LOGGER).addHandler(handler);
            }
            catch (IOException e) {
                Logger.getLogger("com.NoobYang.corejava").log(Level.SEVERE,
                        "Can't creat log file handler",e);
            }
        }
        //@code EventQueue}是一个独立于平台的类，它从底层的对等类和受信任的应用程序类中对事件进行排队。
        EventQueue.invokeLater(() ->
        {
            Handler windowHandler = new WindowHandler();
            windowHandler.setLevel(Level.ALL);
            Logger.getLogger(LOGGER).addHandler(windowHandler);

            JFrame frame = new ImageViewerFrame();
            frame.setTitle("LoggingImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Logger.getLogger(LOGGER).fine("Showing frame");
            frame.setVisible(true);
        });
    }
}
/**
 * The frame that shows the image
 */
class ImageViewerFrame extends JFrame{

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;

    private JLabel label;
    private  Logger logger = Logger.getLogger(LoggingImageViewer.LOGGER);


    public ImageViewerFrame() {
        // 看一下是否有有两个handler
//        for (Handler h :logger.getHandlers()) {
//            System.out.println(h.toString());
//        }
        //Log a method entry  记录方法条目
        logger.entering("ImageViewerFrame", "<init>");
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        //set up menu bar  设置菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("打开文件栏目");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                logger.fine("Exiting");
                System.exit(0);
            }
        });

        //use a label to display the Image  使用标签来显示图像。
        label = new JLabel();
        add(label);
        logger.exiting("ImageViewerFrame","<init>" );
    }


    private class FileOpenListener implements  ActionListener{

        public void actionPerformed (ActionEvent event) {

            logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed",event);

            //set up file chooser
            JFileChooser chooser= new JFileChooser();
            chooser.setCurrentDirectory(new File(""));

            //accept all files ending with .gif
            //接受所有以.gif结尾的文件。
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
            {

                @Override
                public boolean accept(File f) {
                    //返回结尾以小写字母".gif" 或者 目录文件
                    return f.getName().toLowerCase().endsWith(".gif")
                            || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "GIF Image";
                }

            });
            //show file chooser dialog  显示文件选择器对话框
            int r = chooser.showOpenDialog(ImageViewerFrame.this);

            //if image file accepted , set it as icon of the label
            //如果图像文件被接受，将它设置为标签的图标。
            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                logger.log(Level.FINE,"Reading file {0}",name);
                label.setIcon(new ImageIcon(name));
            }
            //取消打开对话框
            else logger.fine("Fine open dialog canceled.");
            logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
        }
    }
}


/**
 * A handler for displaying log records in a window
 * 在窗口中显示日志记录的处理程序。
 */

class WindowHandler extends StreamHandler
{
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private JFrame frame;

    public WindowHandler ()
    {
        frame = new JFrame();
        frame.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        final JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setSize(400,400);
        frame.add(new JScrollPane(output));
        frame.setFocusableWindowState(false);
        frame.setVisible(true);
        setOutputStream(new OutputStream() {

            @Override
            public void write(int b)  {

            }
            public void write(byte[] b,int off ,int len)  {
                output.append(new String(b, off, len));
            }
        });
    }

    public void publish(LogRecord record) {
        if (!frame.isVisible()) {
            return;
        }
        super.publish(record);
        flush();
    }

}