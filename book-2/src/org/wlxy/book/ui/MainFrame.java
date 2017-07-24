package org.wlxy.book.ui;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import org.wlxy.book.service.SaleRecordService;
import org.wlxy.book.ui.SalePanel;
import org.wlxy.book.dao.BookSaleRecordDao;
import org.wlxy.book.dao.SaleRecordDao;
import org.wlxy.book.dao.impl.BookSaleRecordDaoImpl;
import org.wlxy.book.dao.impl.SaleRecordDaoImpl;
import org.wlxy.book.service.impl.SaleRecordServiceImpl;
import org.wlxy.book.dao.BookDao;
import org.wlxy.book.dao.BookInRecordDao;
import org.wlxy.book.dao.BookTypeDao;
import org.wlxy.book.dao.InRecordDao;
import org.wlxy.book.dao.PublisherDao;
import org.wlxy.book.dao.impl.BookDaoImpl;
import org.wlxy.book.dao.impl.BookInRecordDaoImpl;
import org.wlxy.book.dao.impl.BookTypeDaoImpl;
import org.wlxy.book.dao.impl.InRecordDaoImpl;
import org.wlxy.book.dao.impl.PublisherDaoImpl;
import org.wlxy.book.service.BookService;
import org.wlxy.book.service.BookTypeService;
import org.wlxy.book.service.InRecordService;
import org.wlxy.book.service.PublisherService;
import org.wlxy.book.service.impl.BookServiceImpl;
import org.wlxy.book.service.impl.BookTypeServiceImpl;
import org.wlxy.book.service.impl.InRecordServiceImpl;
import org.wlxy.book.service.impl.PublisherServiceImpl;

/**
 * �������JFrame
 * 
 * @author huangato
 * @version  1.0
 *  */
public class MainFrame extends JFrame{
		
	PublisherPanel publisherPanel;
	BookTypePanel bookTypePanel ;
	BookJPanel  bookJPanel;
	RepertoryPanel repertoryPanel ;
	SalePanel salePanel;
	
	//ҵ��ӿ�	
	PublisherService publisherService;
	BookTypeService  bookTypeService ;
	BookService bookService ;
	InRecordService inRecordService ;
    SaleRecordService saleRecordService;

	//����Panel��
    CommonJPanel currentJPanel; 

    private Action sale = new AbstractAction("���۹���", new ImageIcon("images/sale.gif")) {
        public void actionPerformed(ActionEvent e) {
            changePanel(salePanel);
        }
    };
    
	private Action repertory = new AbstractAction("������", new ImageIcon("images/repertory.gif")) {
	        public void actionPerformed(ActionEvent e) {
	            //�л����
	            changePanel(repertoryPanel);
	        }
	    };
    private Action book = new AbstractAction("�鱾����", new ImageIcon("images/book.gif")) {
        public void actionPerformed(ActionEvent e) {
            //�л����
            changePanel(bookJPanel);
        }
    };
	private Action bookType = new AbstractAction("�������", new ImageIcon("images/type.gif")) {
        public void actionPerformed(ActionEvent e) {
            //�л����
            changePanel(bookTypePanel);
        }
    };
	
	private Action publisher = new AbstractAction("���������", new ImageIcon("images/publisher.gif")) {
		public void actionPerformed(ActionEvent e) {
			//�л����
		    changePanel(publisherPanel);
		}
	};

    
	public MainFrame() {
	    //�������ݲ����
		PublisherDao publisherDao = new PublisherDaoImpl();
		BookTypeDao bookTypeDao = new BookTypeDaoImpl(); 
		BookDao  bookDao = new BookDaoImpl();
		InRecordDao  inRecordDao = new InRecordDaoImpl();
		BookInRecordDao bookInRecordDao = new BookInRecordDaoImpl();
		SaleRecordDao saleRecordDao = new SaleRecordDaoImpl();
	    BookSaleRecordDao bookSaleRecordDao = new BookSaleRecordDaoImpl();
		//ҵ������ʵ����
		this.publisherService = new PublisherServiceImpl(publisherDao);	
		this.bookTypeService = new BookTypeServiceImpl(bookTypeDao);
		this.bookService = new BookServiceImpl(bookDao,publisherDao,bookTypeDao);
		this.inRecordService = new InRecordServiceImpl( inRecordDao,  bookDao,  bookInRecordDao);
	    this.saleRecordService = new SaleRecordServiceImpl(saleRecordDao, 
	                bookSaleRecordDao, bookDao);
		//����ܵĲ˵�����
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("ϵͳ");
		menuBar.add(menu);	
	
		//�˵����ӿ�ݷ��ʷ�ʽ
	    menu.add(sale).setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
	    menu.add(repertory).setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));      
		menu.add(book).setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK));	
	    menu.add(bookType).setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));  
	    menu.add(publisher).setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK)); 


		//�ý�����Ϊ��һ��ʾ����
		this.publisherPanel = new PublisherPanel(this.publisherService);
//		this.add(publisherPanel);

	    this.bookJPanel = new BookJPanel(bookService,bookTypeService,publisherService);
//	    this.add(bookJPanel);
	    
	    this.bookTypePanel = new BookTypePanel(this.bookTypeService);
//        this.add(bookTypePanel);
        
        this.repertoryPanel = new RepertoryPanel(inRecordService,bookService);
//        this.add(repertoryPanel);
		
        this.salePanel = new SalePanel(this.bookService, this.saleRecordService);
//        this.add(salePanel);

		this.currentJPanel = this.salePanel;
		this.add(this.salePanel);
		this.salePanel.initData();

//		this.currentJPanel = publisherPanel;
//		this.add(publisherPanel);
//		this.publisherPanel.initData();

		this.setJMenuBar(menuBar);
		this.setTitle("ͼ�����������ϵͳ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		this.setSize(1080, 950);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}	
	 
	   //�л���������
	    private void changePanel(CommonJPanel commonJPanel) {
		//�Ƴ���ǰ��ʾ��JPanel
		this.remove(currentJPanel);
		//������Ҫ��ʾ��JPanel
		this.add(commonJPanel);
		//���õ�ǰ��JPanel
		this.currentJPanel = commonJPanel;
		this.repaint();
		this.setVisible(true);
		//���¶�ȡ����
		commonJPanel.setViewDatas();
		//ˢ���б�
		commonJPanel.clear();
	}
	
	//�������
	public static void main(String[] args) {
		new MainFrame();
	}
}