from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
from docx2pdf import convert
from mailmerge import MailMerge  # 引用邮件处理模块

# datafile_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js002\\js002.xlsx'  # 表格位置
# data = xlrd.open_workbook(datafile_path)
# table = data.sheet_by_name('这是一个合同')  # excel表名
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js003\\NST－04－JS003－2011－委托测试软件功能列表.docx'  # 模版位置
# nrows = table.nrows

with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js003\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  document = MailMerge(template)

  document.merge(
    
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js003\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS014"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
