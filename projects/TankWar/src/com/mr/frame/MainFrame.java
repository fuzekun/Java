package com.mr.frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ������
 * 
 * @author www.mingrisoft.com
 *
 */
public class MainFrame extends JFrame {

	/**
	 * ���췽��
	 */
	public MainFrame() {
		setTitle("̹�˴�ս");// ���ñ���
		setSize(800, 600);// ���ÿ��
		setResizable(false);// ���ɵ�����С
		Toolkit tool = Toolkit.getDefaultToolkit(); // ����ϵͳ��Ĭ��������߰�
		Dimension d = tool.getScreenSize(); // ��ȡ��Ļ�ߴ磬����һ����ά�������
		// ������������Ļ�м���ʾ
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// �رմ���ʱ�޲���
		addListener();// ����¼�����
		setPanel(new LoginPanel(this));// ��ӵ�½���
	}

	/**
	 * ����������
	 */
	private void addListener() {
		addWindowListener(new WindowAdapter() {// ��Ӵ����¼�����
			public void windowClosing(WindowEvent e) {// ����ر�ʱ
				int closeCode = JOptionPane.showConfirmDialog(MainFrame.this, "�Ƿ��˳���Ϸ��", "��ʾ��",
						JOptionPane.YES_NO_OPTION);// ����ѡ��Ի��򣬲���¼�û�ѡ��
				if (closeCode == JOptionPane.YES_OPTION) {// ����û�ѡ��ȷ��
					System.exit(0);// �رճ���
				}
			}
		});
	}

	/**
	 * �����������е����
	 * 
	 * @param panel
	 *            - ���������
	 */
	public void setPanel(JPanel panel) {
		Container c = getContentPane();// ��ȡ����������
		c.removeAll();// ɾ���������������
		c.add(panel);// ����������
		c.validate();// ����������֤�������
	}
}
