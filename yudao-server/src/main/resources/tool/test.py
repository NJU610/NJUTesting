import xlrd  # 引入Excel读取模块
from docx2pdf import convert
from mailmerge import MailMerge  # 引用邮件处理模块

datafile_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\data1.xlsx'  # 表格位置
data = xlrd.open_workbook(datafile_path)
table = data.sheet_by_name('这是一个合同')  # excel表名
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\NST－04－JS007－2011－软件测试报告.docx'  # 模版位置
nrows = table.nrows

document = MailMerge(template)
for i in range(nrows):  
  if i == 0: #此步将忽略第一行的内容
    continue
  document.merge(
    one=str(table.row_values(i)[1]),  
    two=str(table.row_values(i)[2]),  
    three=str(table.row_values(i)[3]), 
    four=str(table.row_values(i)[4]), 
    five=str(table.row_values(i)[5]), 
    six=str(table.row_values(i)[6]), 
    seven=str(table.row_values(i)[7]), 
    eight=str(table.row_values(i)[8]), 
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contrast\\' #输出地址
  o_name = '这是输出的合同' #输出文件名称
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')