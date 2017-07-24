package org.wlxy.book.test;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.wlxy.book.vo.Publisher;
import org.wlxy.book.service.PublisherService;

/**
 * �������JPanel����
 * 
 * @author huangxingbo
 * @version  1.0
 *  */
public class PublisherPanel extends CommonPanel {

    private Vector columns;
    
    private PublisherService service;
    
    //��հ�ť
    JButton clearButton;
    
    //���水ť
    JButton saveButton;
    
    //id(����)
    JTextField PublisherId;
    
    //����������
    JTextField PublisherName;
    
    //��ϵ��
    JTextField pubLinkMan;
    
    //��ϵ�绰
    JTextField pubTel;
    
    //���
    JTextArea pubIntro;
    
    //��ѯ��ť
    JButton queryButton;
    
    //�������Ʋ�ѯ�������
    JTextField queryName;
    
    //��ʼ����
    private void initColumns() {
        this.columns = new Vector();
        this.columns.add("id");
        this.columns.add("����������");
        this.columns.add("��ϵ��");
        this.columns.add("��ϵ�绰");
        this.columns.add("���");
    }

    public PublisherPanel(PublisherService service) {
        this.service = service;
        //��ʼ���м���
        initColumns();
        //�����б�����
        setViewDatas();
        //�����б�
        DefaultTableModel model = new DefaultTableModel(null, this.columns);
        JTable table = new CommonJTable(model);
        setJTable(table);
        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(1000, 350));
        //��������, �޸ĵĽ���
        JPanel downPane = new JPanel();
        downPane.setLayout(new BoxLayout(downPane, BoxLayout.Y_AXIS));
        /*******************************************************/
        Box downBox1 = new Box(BoxLayout.X_AXIS);
        //�½�JTextField����id
        PublisherId = new JTextField();
        //����Ϊ���ɼ�
        PublisherId.setVisible(false);
        downBox1.add(PublisherId);
        //�б������box
        downBox1.add(new JLabel("���������ƣ�"));
        downBox1.add(Box.createHorizontalStrut(10));
        PublisherName = new JTextField(10);
        downBox1.add(PublisherName);
        downBox1.add(Box.createHorizontalStrut(400));
        /*******************************************************/
        Box downBox2 = new Box(BoxLayout.X_AXIS);
        downBox2.add(new JLabel("��  ϵ  �ˣ�"));
        downBox2.add(Box.createHorizontalStrut(10));
        pubLinkMan = new JTextField(10); 
        downBox2.add(pubLinkMan);
        downBox2.add(Box.createHorizontalStrut(50));
        downBox2.add(new JLabel("��ϵ�绰��"));
        downBox2.add(Box.createHorizontalStrut(10));
        pubTel = new JTextField(10);
        downBox2.add(pubTel);
        /*******************************************************/
        Box downBox3 = new Box(BoxLayout.X_AXIS);
        downBox3.add(new JLabel("��飺"));
        downBox3.add(Box.createHorizontalStrut(35));
        pubIntro = new JTextArea("", 5, 5);
        JScrollPane introScrollPane = new JScrollPane(pubIntro);
        //���û���
        pubIntro.setLineWrap(true);
        downBox3.add(introScrollPane);
        /*******************************************************/
        Box downBox4 = new Box(BoxLayout.X_AXIS);
        saveButton = new JButton("����");
        downBox4.add(saveButton);
        downBox4.add(Box.createHorizontalStrut(30));
        clearButton = new JButton("���");
        downBox4.add(clearButton);
        downBox4.add(Box.createHorizontalStrut(30));
        /*******************************************************/
        downPane.add(getSplitBox());
        downPane.add(downBox1);
        downPane.add(getSplitBox());
        downPane.add(downBox2);
        downPane.add(getSplitBox());
        downPane.add(downBox3);
        downPane.add(getSplitBox());
        downPane.add(downBox4);
        /*******************��ѯ******************/
        JPanel queryPanel = new JPanel();
        Box queryBox = new Box(BoxLayout.X_AXIS);
        queryBox.add(new JLabel("���ƣ�"));
        queryBox.add(Box.createHorizontalStrut(30));
        queryName = new JTextField(20);
        queryBox.add(queryName);
        queryBox.add(Box.createHorizontalStrut(30));
        queryButton = new JButton("��ѯ");
        queryBox.add(queryButton);
        queryPanel.add(queryBox);
        this.add(queryPanel);
        //�б�Ϊ���ӽ���
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, downPane);
        split.setDividerSize(5);
        this.add(split);
        //��ʼ��������
        initListeners();
    }
    
    //��ʼ��������
    private void initListeners() {
        //����ѡ�������
        getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                //��ѡ����ʱ����ͷ�ʱ��ִ��
                if (!event.getValueIsAdjusting()) {
                    //���û��ѡ���κ�һ��, �򷵻�
                    if (getJTable().getSelectedRowCount() != 1) return;
                    //���ø���ķ������ѡ���е�id
                    String id = getSelectId(getJTable());
                    view(id);
                }
            }
        });
        //��հ�ť������
        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                clear();
            }
        });
        //���水ť������
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                save();
            }
        });
        //��ѯ��ť������
        queryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                query();
            }
        });
    }
    
    //������ģ����ѯ
    private void query() {
        String name = this.queryName.getText();
        Vector<Publisher> Publishers = (Vector<Publisher>)service.queryByName(name);
        //ת�����ݸ�ʽ
        Vector<Vector> datas =  changeDatas(Publishers);
        //��������
        setDatas(datas);
        //ˢ���б�
        refreshTable();
    }
    
    //�鿴һ��������
    private void view(String id) {
        Publisher c = service.queryByID(id);
        //����ID��JTextFiled�����أ�
        this.PublisherId.setText(c.getID().toString());
        //���ó��������Ƶ��ı���
        this.PublisherName.setText(c.getPUB_NAME());
        //������ϵ�˵��ı���
        this.pubLinkMan.setText(c.getPUB_LINK_MAN());
        //������ϵ�绰���ı���
        this.pubTel.setText(c.getPUB_TEL());
        //���ü����ı���
        this.pubIntro.setText(c.getPUB_INTRO());
    }
    
    //���淽��
    private void save() {
        if (this.PublisherName.getText().trim().equals("")) {
            //���ø���ķ���������������ʾ
            showWarn("���������������");
            return;
        }
        //���idΪ��, ��Ϊ����, ��Ϊ����Ϊ�޸Ĳ���
        if (this.PublisherId.getText().equals("")) add();
        else update();
    }
    
    //��������
    private void add() {
        //��ý����е�ֵ����װ�ɶ���
        Publisher c = getPublisher();
        service.add(c);
        //���¶�ȡ����
        setViewDatas();
        //ˢ�±���
        clear();
    }
    
    //�޸ķ���
    private void update() {
        //ȡ�õ�ǰ�޸ļ�¼��ID
        String id = this.PublisherId.getText();
        //���ݽ������ݻ��Publisher����
        Publisher c = getPublisher();
        c.setID(id);
        service.update(c);
        //���¶�ȡ����
        setViewDatas();
        //ˢ���б�
        refreshTable();
    }
    
    //�ӱ����л�ȡ���ݲ���װ��Publisher����, û��id
    private Publisher getPublisher() {
        String PublisherName = this.PublisherName.getText();
        String pubTel = this.pubTel.getText();
        String pubLinkMan = this.pubLinkMan.getText();
        String pubIntro = this.pubIntro.getText();
        return new Publisher(null, PublisherName, pubTel, pubLinkMan, pubIntro);
    }
    
    //��ձ�����ˢ���б�
    public void clear() {
        //ˢ���б�
        refreshTable();
        //��ձ����еĸ����ı����򣩵�ֵ
        this.PublisherId.setText("");
        this.PublisherName.setText("");
        this.pubLinkMan.setText("");
        this.pubTel.setText("");
        this.pubIntro.setText("");
    }

    @Override
    public Vector<String> getColumns() {
        return this.columns;
    }

    /*
     * ���ñ�����ʽ(non-Javadoc)
     * @see org.crazyit.book.ui.CommonPanel#setTableFace()
     */
    public void setTableFace() {
        //����id��
        getJTable().getColumn("id").setMinWidth(-1);
        getJTable().getColumn("id").setMaxWidth(-1); 
        getJTable().getColumn("���").setMinWidth(400);
        getJTable().setRowHeight(30);
    }
    
    /**
     * ������ת������ͼ����ĸ�ʽ
     * @param datas
     * @return
     */
    private Vector<Vector> changeDatas(Vector<Publisher> datas) {
        Vector<Vector> view = new Vector<Vector>();
        for (Publisher c : datas) {
            Vector v = new Vector();
            v.add(c.getID());
            v.add(c.getPUB_NAME());
            v.add(c.getPUB_LINK_MAN());
            v.add(c.getPUB_TEL());
            v.add(c.getPUB_INTRO());
            view.add(v);
        }
        return view;
    }
    
    /*
     * ʵ�ָ��෽��, ��ѯ���ݿⲢ���ض�Ӧ�����ݸ�ʽ, ���ø����setDatas�����������ݼ���
     */
    public void setViewDatas() {
        //ʹ��ҵ��ӿڵõ�����
        Vector<org.wlxy.book.vo.Publisher> Publishers = (Vector<Publisher>)service.queryAll();
        //������ת������ʾ��ʽ
        Vector<Vector> datas =  changeDatas(Publishers);
        //���ø����setDatas����
        setDatas(datas);
    }

    
}