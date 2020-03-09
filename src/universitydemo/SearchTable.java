/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universitydemo;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 101xo
 */
public class SearchTable extends javax.swing.JFrame {

    /**
     * Creates new form SearchTable
     */
    String attribute, tableName, attributeName, search;
    Boolean studentID;
    String deptName;
    String authorName;
    int courseNumber;
    double cgpa;
    boolean above;
    String desigSearch;
    boolean highest;
    
    
    SearchTable(String tableName, String attributeName, String search, String attribute, Boolean studentID) {
        this.tableName = tableName;
        this.attributeName = attributeName;
        this.search = search;
        this.attribute = attribute;
        this.studentID = studentID;
        
        initComponents();
        try {
            showSearch();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    SearchTable(String deptName,String authorName) {
        this.deptName=deptName;
        this.authorName=authorName;
        initComponents();
        try {
            secondSearch();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    SearchTable(String deptName,int courseNumber)
    {
        initComponents();
        this.deptName=deptName;
        this.courseNumber=courseNumber;
        try {
            thirdSearch();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    SearchTable(boolean above,double cgpa,String desigSearch)
    {
        initComponents();
        this.above=above;
        this.cgpa=cgpa;
        this.desigSearch=desigSearch;
        try {
            forthSearch();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    SearchTable(String deptNameorQuery)
    {
        initComponents();
        
        if(deptNameorQuery.equals("same"))
        {
            try {
                ninthSearch();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else{
            this.deptName=deptNameorQuery;
        try {
            fifthSearch();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
    }
    SearchTable(boolean highest,String deptName)
    {
        initComponents();
        this.highest=highest;
        this.deptName=deptName;
        try {
            sixthSearch();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    SearchTable(boolean count)
    {
        initComponents();
        if(count){
            try {
                eighthSearch();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                seventhSearch();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
    }
    SearchTable() {
        initComponents();//To change body of generated methods, choose Tools | Templates.
    }
    private void ninthSearch() throws ClassNotFoundException, SQLException
    {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement = null;
         preparedStatement = connection.prepareStatement(" SELECT f.name, s.name from Student  f, "
                 + "Student s where left(f.dob,6) = left(s.dob,6) and f.student_id < s.student_id");
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
    }
    private void eighthSearch() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement = null;
         preparedStatement = connection.prepareStatement(" select (select name from Department where "
                 + "dept_id = Student.dept_id) as [dept. name],count(distinct Student.name) as [Student no.],count(distinct Faculty.name) [Faculty no.],count(distinct Staff.name) as [Staff no.],count(distinct Student.name) + count(distinct Faculty.name) + count(distinct Staff.name) as total from Student join Faculty on "
                 + "Student.dept_id = Faculty.dept_id join Staff on Student.dept_id= Staff.dept_id group by Student.dept_id");
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
      
    }
    private void seventhSearch() throws ClassNotFoundException, SQLException
    {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement = null;
         preparedStatement = connection.prepareStatement(" select Student.student_id,name,(select name from Department where dept_id = Student.dept_id) as [dept. name] from Student left join Course_student on "
                                                            + "Student.student_id = Course_student.student_id where Course_student.student_id is null ");
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
      
    }
    private void sixthSearch() throws ClassNotFoundException, SQLException
    {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement = null;
        if(deptName.equals("ALL"))
        {
            if(highest)
                preparedStatement = connection.prepareStatement(" select top 3 student_id,name,cgpa from Student where dept_id in (select dept_id from Department) order by cgpa DESC");
            else
              preparedStatement = connection.prepareStatement(" select top 3 student_id,name,cgpa from Student where dept_id in (select dept_id from Department) order by cgpa ASC");
              
        }
        else
        {
            if(highest)
            {
                preparedStatement = connection.prepareStatement(" select top 3 student_id,name,cgpa from Student where dept_id in (select dept_id from Department where name=?) order by cgpa DESC");
                preparedStatement.setString(1, deptName);
            }
            else
            { 
                preparedStatement = connection.prepareStatement(" select top 3 student_id,name,cgpa from Student where dept_id in (select dept_id from Department where name=?) order by cgpa ASC");
                preparedStatement.setString(1, deptName);
                } 
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
  
    }
    private void fifthSearch() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement = null;
        if(deptName.equals("ALL"))
        {
         preparedStatement = connection.prepareStatement(" select * from Student where student_id in "
                 + "(select student_id from Student where dept_id in  (select dept_id from Department) "
                 + "except (select student_id from BooksOfStudent))");
        }
        else {
            preparedStatement = connection.prepareStatement(" select * from Student where student_id in (select student_id from "
                    + "Student where dept_id = (select dept_id from Department where name = ?) except (select student_id from BooksOfStudent))");
        preparedStatement.setString(1, deptName);
        }
         ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
  
    }
    private void forthSearch() throws ClassNotFoundException, SQLException
    {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement = null;
        if(above)
        {
            preparedStatement = connection.prepareStatement("select Student.student_id,Student.name as [student name],cgpa,Course_student.course_id,Faculty.name as [faculty name] from Student join Course_student on Course_student.student_id = Student.student_id join Course_faculty on "
                                                             + "Course_student.course_id = Course_faculty.course_id join "
                                                                + "Faculty on Course_faculty.faculty_id = Faculty.faculty_id where cgpa > "+cgpa+" and designation = ?");
          
        }
        else
        {
            preparedStatement = connection.prepareStatement("select Student.student_id,Student.name as [student name],cgpa,Course_student.course_id,Faculty.name as [faculty name] from Student join Course_student on Course_student.student_id = Student.student_id join Course_faculty on "
                                                             + "Course_student.course_id = Course_faculty.course_id join "
                                                                + "Faculty on Course_faculty.faculty_id = Faculty.faculty_id where cgpa < "+cgpa+" and designation = ?");
          
        }
        preparedStatement.setString(1,desigSearch);
         ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
  
    }
    private void thirdSearch () throws ClassNotFoundException, SQLException
    {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement;
        
        
            preparedStatement = connection.prepareStatement("select Course_id,(select name from Course_faculty where course_id = ?) "
                    + "as [course name],(select name from Department where dept_id = Student.dept_id) as department ,COUNT(dept_id) as [number of student] "
                    + "from Course_student join Student on Course_Student.student_id = Student.student_id where course_id = ? "
                    + "and dept_id = (select dept_id from Department where name = ?) group by course_id,dept_id");
            preparedStatement.setInt(1, courseNumber);
            preparedStatement.setInt(2, courseNumber);
            preparedStatement.setString(3, deptName);
             System.out.println(courseNumber);
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
  
    }
    private void secondSearch() throws ClassNotFoundException, SQLException
    {
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement;
        if (deptName.equals("")) {
            preparedStatement = connection.prepareStatement("select student_id,Student.name from Student join "
                                                                +"Book_borrower on student_id = borrower_id where book_id in "
                                                                +"(select book_id from Book where author = ?)");
          preparedStatement.setString(1, authorName);
        } else {
            preparedStatement = connection.prepareStatement("select   student_id,Student.name from Student join Book_borrower on student_id = borrower_id where "
                                                                + "dept_id = (select dept_id from Department where name = ?) "
                                                                + "and book_id in (select book_id from Book where author = ?)");
            preparedStatement.setString(1, deptName);
            preparedStatement.setString(2, authorName);
           
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        deleteButton.setVisible(false);
    }
    private void showSearch() throws ClassNotFoundException, SQLException {
        //System.out.println(attributeName);
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
        PreparedStatement preparedStatement;
        if (search.equals("")) {
            preparedStatement = connection.prepareStatement("SELECT "+ attribute+" FROM " + tableName + "");
        } else {
            preparedStatement = connection.prepareStatement("SELECT " + attribute + " FROM " + tableName + " where " + attributeName + "=? ");
            preparedStatement.setString(1, search);
        }

        

        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData rsmetadata = resultSet.getMetaData();
        int columns = rsmetadata.getColumnCount();
        DefaultTableModel dtm = new DefaultTableModel();
        Vector columns_name = new Vector();
        Vector data_rows = new Vector();
        for (int i = 1; i < columns + 1; i++) {
            columns_name.addElement(rsmetadata.getColumnLabel(i));
        }

        dtm.setColumnIdentifiers(columns_name);
        while (resultSet.next()) {
            data_rows = new Vector();
            for (int j = 1; j < columns + 1; j++) {
                data_rows.addElement(resultSet.getString(j));
            }
            dtm.addRow(data_rows);

        }
        jTable1.setModel(dtm);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deleteButton)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        String deleteRow = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        String colName = jTable1.getColumnName(0);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Project;integratedSecurity=true");
            PreparedStatement preparedStatement;
            if (studentID) {
                
                preparedStatement = connection.prepareStatement("Delete from " + tableName + " where student_id=?");
                preparedStatement.setInt(1, Integer.parseInt(deleteRow));
                preparedStatement.execute();
                 
            }
            else{
            preparedStatement = connection.prepareStatement("Delete from " + tableName + " where student_id=(select student_id where " + colName + "=?) ");
            preparedStatement.setString(1, deleteRow);
            preparedStatement.execute();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Row has been Deleted");
        ((DefaultTableModel) jTable1.getModel()).removeRow(jTable1.getSelectedRow());


    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchTable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
