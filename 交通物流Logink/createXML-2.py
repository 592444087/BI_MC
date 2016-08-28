import xlrd
import xlwt
import re
#添加父节点
mode = re.compile(r'\d+')
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
target_table.write(0, 4, "字段长度")
for rownum in range(1,nrows):
    node_id = round(float(table.cell(rownum,0).value))
    layer = round(float(table.cell(rownum,1).value))
    node_name = table.cell(rownum,2).value
    parent_node[layer] = node_id
    str_length = mode.findall(table.cell(rownum,5).value)
    if len(str_length)==0:
        str_l = ''
    else:
        str_l = int(str_length[0])


    target_table.write(rownum, 0, node_id)
    target_table.write(rownum, 1, layer)
    target_table.write(rownum, 2, node_name)
    target_table.write(rownum, 3, parent_node[layer-1])
    target_table.write(rownum, 4, str_l)
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
        if table.cell(layer,4).value != '':
            i = 0
            con = ''
            while i < int(round(table.cell(layer,4).value)):
                i=i+1
                con = con + 'a'
        temp_xml = "<" + table.cell(layer,2).value + ">" + con + "</" + table.cell(layer,2).value + ">"
        print(temp_xml)
    if int(round(float(table.cell(layer,1).value))) == int(round(float(table.cell(layer+1,1).value)))-1:
        temp_xml ="<" + table.cell(layer,2).value + ">"
        print(temp_xml)
    if int(round(float(table.cell(layer,1).value))) > int(round(float(table.cell(layer+1,1).value))):
        parent_node = int(round(float(table.cell(layer,3).value)))
        if table.cell(layer,4).value != '':
            i = 0
            con = ''
            while i < int(round(table.cell(layer,4).value)):
                i=i+1
                con += 'a'
        temp_xml ="<" + table.cell(layer,2).value + ">" + con + "</" + table.cell(layer,2).value + ">"
        print(temp_xml)
        next_layer = int(round(float(table.cell(layer+1,1).value)))
        while(int(round(float(table.cell(parent_node,1).value))) >= next_layer):
            temp_xml = "</" + table.cell(parent_node,2).value + ">"
            print(temp_xml)
            parent_node = int(round(float(table.cell(parent_node,3).value)))
temp_xml = "</Root>"
print(temp_xml)