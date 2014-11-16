package com.vote.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vote.bean.Answer;
import com.vote.bean.Replay;

public class ReplayService {

	/**
	 * 获取所有问题回答情况
	 * @param oid 主题ID
	 * @return
	 */
	public static Map<Integer,List<Map<Integer,Integer>>> getAllAnswer(int oid) {
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		int qCount = 0;
		int sCount = 0;
		int qaCount = 0;
		int saCount = 0;
		Map<Integer,List<Map<Integer,Integer>>> map = new HashMap<Integer,List<Map<Integer,Integer>>>();
		try {
			con = db.getConnection();
			qCount = getQuesCount(con,oid);
			for(int i=1;i<=qCount;i++)
			{
				List<Map<Integer,Integer>> list = new ArrayList<Map<Integer,Integer>>();
				qaCount = getAnswerCount(con,oid,i);
				sCount = getSelCount(con,oid,i);
				for(int j=0;j<=sCount;j++)
				{
					Map<Integer,Integer> m = new HashMap<Integer,Integer>();
					if(j==0){
						m.put(0, qaCount);
					}else{
						saCount = getAnswerCount(con,oid,i,j);
						m.put(j, saCount);
					}
					list.add(m);
				}
				map.put(i, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.closeAll(con, stm, rs);
		}
		return map;
	}
	
	public static List<Answer> getAnswers(int oid,int qSeq)  {
		DBConnection dbcon = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		List<Answer> answers = new LinkedList<Answer>();
		String sql = "select * from wj_answer where oid='"+ oid + "' and qSeq="+qSeq+" order by answerId asc";
		try {
			dbcon=new DBConnection();
			con=dbcon.getConnection();
			stm=con.createStatement();
			 rs = stm.executeQuery(sql);
			 while(rs.next()){
				 Answer answer = new Answer();
				 int answerId = rs.getInt("answerId");
				 int replayId = rs.getInt("replayId");
				 int oidd = rs.getInt("oid");
				 int qSeqq = rs.getInt("qSeq");
				 int seSeq = rs.getInt("seSeq");
				 String seValue = rs.getString("seValue");
				 String remark = rs.getString("remark");
				 
				 answer.setAnswerId(answerId);
				 answer.setReplayId(replayId);
				 answer.setOid(oidd);
				 answer.setqSeq(qSeqq);
				 answer.setSeSeq(seSeq);
				 answer.setSeValue(seValue);
				 answer.setRemark(remark);
				
				 answers.add(answer);
		 }
			return answers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbcon.closeAll(con, stm, rs);
		}
	}
	
	/**
	 * 得到问题总数
	 * @param con
	 * @param oid
	 * @return
	 */
	public static int getQuesCount(Connection con, int oid) {
		Statement stm = null;
		ResultSet rs = null;
		int qcount = 0;
		try {
			stm=con.createStatement();
			String sql = "select count(*) from wj_question where oid=" + oid;
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				qcount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.free(stm, rs);
		}
//		System.out.println("问题的总数" + qcount);
		return qcount;
	}
	
	/**
	 * 得到选项总数
	 * @param con
	 * @param oid
	 * @return
	 */
	public static int getSelCount(Connection con, int oid, int qSeq) {
		Statement stm = null;
		ResultSet rs = null;
		int qcount = 0;
		try {
			stm=con.createStatement();
			String sql = "select count(*) from wj_selecter where oid=" + oid +" and qseq ="+qSeq;
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				qcount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.free(stm, rs);
		}
//		System.out.println("选项的总数" + qcount);
		return qcount;
	}
	
	/**
	 * 根据主题ID查询回复总数
	 * @param oid 主题ID
	 * @return
	 */
	public static int getAnswerCount(int oid) {
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		String sql = "";
		int rcount = 0;
		try {
			con = db.getConnection();
			stm = con.createStatement();
			sql = "select count(*) from wj_replay where oid =" + oid;
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				rcount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.closeAll(con, stm, rs);
		}
//		System.out.println("回复人数总数rcount:" + rcount);
		return rcount;
	}
	
	/**
	 * 根据主题Id和题目序号查询该问题的回答数
	 * @param con 数据库连接
	 * @param oid 主题Id
	 * @param qSeq 题目序号
	 * @return 回答数
	 */
	public static int getAnswerCount(Connection con, int oid,int qSeq) {
		Statement stm=null;
		ResultSet rs=null;
		String sql = "";
		int rcount = 0;
		try {
			stm = con.createStatement();
			sql = "select count(*) from wj_answer where oid =" + oid +" and qSeq="+qSeq;
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				rcount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.free(stm, rs);
		}
//		System.out.println("问题的回答数rcount:" + rcount);
		return rcount;
	}
	
	/**
	 * 根据主题Id和题目序号和选项序号查询该问题选项的回答数
	 * @param con 数据库连接
	 * @param oid 主题Id
	 * @param qSeq 题目序号
	 * @param seSeq 选项序号
	 * @return 问题选项的回答数
	 */
	public static int getAnswerCount(Connection con, int oid,int qSeq,int seSeq) {
		Statement stm=null;
		ResultSet rs=null;
		String sql = "";
		int rcount = 0;
		try {
			stm = con.createStatement();
			sql = "select count(*) from wj_answer where oid =" + oid +" and qSeq="+qSeq +" and seSeq="+seSeq;
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				rcount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.free(stm, rs);
		}
//		System.out.println("问题选项的回答数rcount:" + rcount);
		return rcount;
	}
	
	/**
	 * 将回复信息存储到数据库
	 * @param rList
	 * @return
	 */
	public static boolean save(Replay r,List<Answer> answers) {
		DBConnection db = new DBConnection();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql = "";
		int count = 0;
		int replayId = 0;
		boolean flag = false;
		boolean defaultAutoCommit = true;
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		try {
			con = db.getConnection();
			defaultAutoCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			sql = "insert into wj_replay(replayCode,replayIp,oid,replayTime,remark) values (?,?,?,?,?)";
			System.out.println(sql);
			stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, r.getReplayCode());
			stmt.setString(2, r.getReplayIp());
			stmt.setInt(3, r.getoId());
			stmt.setTimestamp(4, r.getReplayTime());
			stmt.setString(5, r.getRemark());
			
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) replayId = rs.getInt(1);
			System.out.println("replayId: "+replayId);
			
			sql = "insert into wj_answer(replayId,oid,qSeq,seSeq,seValue,remark) values (?,?,?,?,?,?)";
			
			for(int i=0;i<answers.size();i++)
			{
				Answer a = answers.get(i);
				a.setRemark(getRemark(a.getOid(),a.getqSeq(),a.getSeSeq()));
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, replayId);
				stmt.setInt(2, a.getOid());
				stmt.setInt(3, a.getqSeq());
				stmt.setInt(4, a.getSeSeq());
				stmt.setString(5, a.getSeValue());
				stmt.setString(6, a.getRemark());
				stmt.executeUpdate();
				
				count++;
			}
			con.commit();
			con.setAutoCommit(defaultAutoCommit);
			flag = true;
			System.out.println("插入表[wj_answer] "+count+" 条记录");
			System.out.println(replayId);
			boolean falg = totalScore(replayId, r.getoId());
		} catch (Exception e) {
			try {
				if(con!=null){
					con.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			db.closeAll(con, stmt, rs);
		}
		return flag;
	}
	/**
	 * 获取回答问题的分数
	 * @param oid 问卷编号
	 * @param qseq 问题编号
	 * @param seseq 选项编号
	 * @return 分数
	 */
	public static String getRemark(int oid ,int qseq, int seseq){
		String remark = new String();
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		String sql = "";
		try {
			con = db.getConnection();
			stm = con.createStatement();
			sql = "select remark from wj_selecter where oid =" + oid +" and qSeq="+qseq +" and selseq="+seseq;
			rs = stm.executeQuery(sql);
			while(rs.next()){
				remark = rs.getString("remark");
			}
			if(remark.equals(null)){
				remark = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.free(stm, rs);
		}
		return remark;
	}
	
	// 删除问卷回复情况
	public static boolean delReplay(int oid) throws Exception {
		DBConnection dbcon = new DBConnection();
		boolean flag = false;
		Connection conn = dbcon.getConnection();
		conn.setAutoCommit(false);
		String sql1 = "delete from wj_replay where oid="+oid;
		String sql2 = "delete from wj_answer where oid="+oid;
		try {
			dbcon.update(sql1, conn);
			dbcon.update(sql2, conn);
			conn.commit();
			flag = true;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				dbcon.closeAll(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否存在回复
	 * @param oid
	 * @param code
	 * @return
	 */
	public static boolean exit(int oid,String replayIp,String replayCode) {
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		String sql = "";
		boolean falg = false;
		int qcount = 0;
		try {
			con = db.getConnection();
			stm = con.createStatement();
			sql = "select count(*) from wj_replay r where oid="+oid;
			if(replayIp!=null&&!replayIp.trim().equals("")) sql +=" and r.replayIp='"+replayIp+"'";
			if(replayCode!=null&&!replayCode.trim().equals("")) sql +=" and r.replayCode='"+replayCode+"'";
			System.out.println(sql);
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				qcount = rs.getInt(1);
			}
			if(qcount>0) falg=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.closeAll(con, stm, rs);
		}
		return falg;
	}
	/**
	 * 回答者回答该问卷所获的分数
	 * @param replayid 回答问题的人Id
	 * @param oid 问卷id
	 * @return 是否成功插入分数
	 */
	public static boolean totalScore(int replayid ,  int oid){
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		String sql = "";
		int score = 0;
		try{
			con = db.getConnection();
			stm = con.createStatement();
			sql = "select remark from wj_answer where oid =" + oid +" and replayid="+replayid ;
			System.out.println(sql);
			rs = stm.executeQuery(sql);
			while(rs.next()){
				if(rs.getString("remark").equals(null)){
					score += 0 ;
				}else{
					score += Integer.valueOf(rs.getString("remark"));
				}
			}
			upScore(score,replayid);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return true;
	}
	public static void upScore(int score,int replayid){
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		String sql = "";
		try{
			con = db.getConnection();
			stm = con.createStatement();
			sql = "update wj_replay set remark ='"+score+"' where replayid ="+replayid ;
			System.out.println(sql);
			stm.executeUpdate(sql) ;		// 执行数据库更新操作
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 返回查询排序后的回答名单
	 * @param oid 问卷编号
	 * @return  回答名单
	 */
	public static List<Replay> sort(int oid){
		List<Replay> replay = new ArrayList<Replay>();
		DBConnection db = new DBConnection();
		Connection con=null;
		Statement stm=null;
		ResultSet rs=null;
		String sql = "select * from wj_replay where oid="+oid+" order by remark desc";
		try{
			con = db.getConnection();
			stm = con.createStatement();
			System.out.println(sql);
			rs = stm.executeQuery(sql);	//数据库查询
			while(rs.next()){
				Replay r = new Replay();
				r.setReplayId(rs.getInt("replayid"));
				r.setRemark(rs.getString("remark"));
				System.out.println(r.getReplayId());
				System.out.println(r.getRemark());
				replay.add(r);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stm.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return replay;
	}
}
