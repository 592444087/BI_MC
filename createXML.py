import xlrd
import xlwt
#添加父节点
src_path = "D:\\工作簿1.xlsx"
package_data = xlrd.open_workbook(src_path)
table = package_data.sheets()[0]
nrows = table.nrows
file = xlwt.Workbook()
target_table = file.add_sheet('sheet1')

parent_node={0:0}
ctype = 2
xf = 0
target_table.write(0, 0, "序号")
target_table.write(0, 1, "报文层")
target_table.write(0, 2, "英文名称")
target_table.write(0, 3, "父节点编号")
for rownum in range(1,nrows):
    node_id = round(float(table.cell(rownum,0).value))
    layer = round(float(table.cell(rownum,1).value))
    node_name = table.cell(rownum,2).value
    parent_node[layer] = node_id

    target_table.write(rownum, 0, node_id)
    target_table.write(rownum, 1, layer)
    target_table.write(rownum, 2, node_name)
    target_table.write(rownum, 3, parent_node[layer-1])
file.save('D:\\demo.xls')

#生成xml
src_path = "D:\\demo.xls"
package_data = xlrd.open_workbook(src_path)
table = package_data.sheets()[0]
nrows = table.nrows
temp_xml = ""
print(nrows)
for layer in range(1,nrows-1):
    if table.cell(layer,1).value == table.cell(layer+1,1).value:
        temp_xml = temp_xml + "<" + table.cell(layer,2).value + "/>\r\n"
    if int(round(float(table.cell(layer,1).value))) == int(round(float(table.cell(layer+1,1).value)))-1:
        temp_xml = temp_xml +  "<" + table.cell(layer,2).value + ">\r\n"
    if int(round(float(table.cell(layer,1).value))) > int(round(float(table.cell(layer+1,1).value))):
        parent_node = int(round(float(table.cell(layer,3).value)))
        temp_xml += "<" + table.cell(layer,2).value + "/>\r\n"
        next_layer = int(round(float(table.cell(layer+1,1).value)))
        while(int(round(float(table.cell(parent_node,1).value))) >= next_layer):
            temp_xml += "</" + table.cell(parent_node,2).value + ">\r\n"
            parent_node = int(round(float(table.cell(parent_node,3).value)))
temp_xml += "</Root>"
print(temp_xml)