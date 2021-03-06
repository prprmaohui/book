package org.wlxy.book.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.wlxy.book.vo.BookType;
import org.wlxy.book.dao.impl.CommonDaoImpl;
import org.wlxy.book.dao.BookTypeDao;


/**
 * @ClassName BookBookTypeDaoImpl
 * @Description TODO(书的种类数据层接口)
 * @author huangtao
 * @Date 2017年7月10日 上午10:43:41
 * @version 1.0.0
 */
public class BookTypeDaoImpl extends CommonDaoImpl implements BookTypeDao {

 
    @Override
    public Collection<BookType> findByName(String name) {
        String sql = "SELECT * FROM T_BOOK_TYPE t WHERE t.TYPE_NAME like '%" + name + "%' " +
                "ORDER BY t.id DESC";
        List<BookType> datas =  (List<BookType>)getDatas(sql.toString(), new Vector(), BookType.class);
        return datas;
    }

    @Override
    public Collection<BookType> findAll() {
        String sql = "select * from T_BOOK_TYPE t ORDER BY t.ID DESC";
        return getDatas(sql, new Vector(), BookType.class);
    }
  
    @Override
    public BookType findByID(String id) {
        String sql = "SELECT * FROM T_BOOK_TYPE t WHERE t.ID=" + id;
        List<BookType> datas =  (List<BookType>)getDatas(sql, new ArrayList(), BookType.class);
        return datas.get(0);
    }

    @Override
    public String update(BookType bookType) {
        String sql = "UPDATE T_BOOK_TYPE t SET t.type_name='" + bookType.getTYPE_NAME() +
                "', t.type_intro='" + bookType.getTYPE_INTRO() + "' WHERE t.id='" + bookType.getID() + "'";
                getJDBCExecutor().executeUpdate(sql);
                return bookType.getID();
    }
    
    @Override
    public String save(BookType bookType) {
        String sql = "INSERT INTO T_BOOK_TYPE VALUES (ID, '" +
                bookType.getTYPE_NAME() + "', '" + bookType.getTYPE_INTRO() + "')";
                String id = String.valueOf(this.getJDBCExecutor().executeUpdate(sql));
                return id;
    }

}
