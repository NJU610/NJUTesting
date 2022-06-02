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
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS010\\NST－04－JS010－2011－测试报告检查表.docx'  # 模版位置
# nrows = table.nrows

with open("C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS010\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  document = MailMerge(template)

  document.merge(
    Del = jdata["委托单位"],
    SoftName = jdata["软件名称"],
    Reviewer = jdata["检查人"],
    Date = jdata["日期"],
    
    Res1 = jdata["dataSource"][0]["result"],
    Res2 = jdata["dataSource"][1]["result"],
    Res3 = jdata["dataSource"][2]["result"],
    Res4 = jdata["dataSource"][3]["result"],
    Res5 = jdata["dataSource"][4]["result"],
    Res6 = jdata["dataSource"][5]["result"],
    Res7 = jdata["dataSource"][6]["result"],
    Res8 = jdata["dataSource"][7]["result"],
    Res9 = jdata["dataSource"][8]["result"],
    Res10 = jdata["dataSource"][9]["result"],
    Res11 = jdata["dataSource"][10]["result"],
    Res12 = jdata["dataSource"][11]["result"],
    Res13 = jdata["dataSource"][12]["result"],
    Res14 = jdata["dataSource"][13]["result"],
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS010\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS004"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
