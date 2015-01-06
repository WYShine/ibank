package demo.main.operator;


import demo.main.operator.menu.ShowOperatorFrame;
import gui.iBankGui;
import ibankapi.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class QueryOperatorFrame extends iBankGui
{
	private static final long serialVersionUID = 503L;

	private JTextField textOrgId;
	private JTextField textOperatorId;


	public QueryOperatorFrame(JFrame parent)
	{
		super(parent);

		setTitle("iBank Query Operator Demo");

		JLabel lbOrgId = CreateLable("机构编号");
		JLabel lbOperatorId = CreateLable("操作员编号");


		textOrgId = new JTextField();
		textOrgId.setColumns(15);
		textOrgId.addKeyListener(keyListener);

		textOperatorId = new JTextField();
		textOperatorId.setColumns(15);
		textOperatorId.addKeyListener(keyListener);

		lbTitle.setText("查询操作员");
		btnOK.setText("查询");
		btnOK.addKeyListener(keyListener);


		SetFont(textOperatorId);
		SetFont(textOrgId);


		AddInputComponent(lbOrgId, 0, 0, 8, 1);
		AddInputComponent(textOrgId, 8, 0, GridBagConstraints.RELATIVE, 1);
		AddInputComponent(lbOperatorId, 0, 1, 8, 1);
		AddInputComponent(textOperatorId, 8, 1, GridBagConstraints.RELATIVE, 1);
		AddInputComponent(btnOK, 0, 2, 8, 1);


	}

	protected void TransactionAction()
	{
		super.TransactionAction();

		if (textOperatorId.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null,"请输入操作员ID","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean bRet;
		HashMap<String, String> data = new HashMap<String, String>();

		Transaction Trans = new Transaction("100063");

		data.put("ORGID",textOrgId.getText());
		data.put("OPID",textOperatorId.getText());

		bRet = Trans.Init();

		if (!bRet) {
			ShowStatusMessage(Trans.GetStatusMsg());
			return;
		}

		bRet = Trans.SendMessage(data);
		if (!bRet) {
			ShowStatusMessage(Trans.GetStatusMsg());
			return;
		}
		ShowStatusMessage(Trans.GetStatusMsg());

		if(!Trans.GetStatus())
			return;

		String[] tmp = {"NAME1","GENDER","PASSWD","CONNEC","TYPE","AUTH"};
		for(String x : tmp)
		{
			data.put(x, Trans.GetResponseValue(x));
		}

		Trans.Release();

		ShowOperatorFrame showOperatorFrame = new ShowOperatorFrame(this,data);
		OpenTransWindow(showOperatorFrame);


	}
}
