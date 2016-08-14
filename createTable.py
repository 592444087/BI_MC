import xlrd

src_path = "D:\\工作簿1.xlsx"

transport_data = xlrd.open_workbook(src_path)

for index in range(0,6):


    table = transport_data.sheets()[index]

    nrows = table.nrows

    ncols = table.ncols

    table_name = table.cell(0,1).value

    ddl_string = "CREATE TABLE " + table_name + "(\n"
    comment_string = ""
    for i in range(1,nrows):

        ddl_string += table.cell(i,1).value + " " + table.cell(i,2).value + ",\n"
        comment_string += "COMMENT ON COLUMN " + table_name + "." + table.cell(i,1).value + " IS '" + table.cell(i,0).value + "';\n\n"
    ddl_string = ddl_string[:len(ddl_string)-2] + ");\n"

    file_path = "C:\\Users\\wu\\Desktop\\sql\\" + table_name + ".sql"
    f = open(file_path,'w')
    f.write(ddl_string+comment_string)
    f.close()
