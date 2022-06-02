from signal import SIGABRT
from sqlite3 import DatabaseError
from tkinter import PROJECTING
import xlrd  # 引入Excel读取模块
import json
import xlwt
from docx2pdf import convert
from mailmerge import MailMerge  # 引用邮件处理模块

# datafile_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js002\\js002.xlsx'  # 表格位置
# data = xlrd.open_workbook(datafile_path)
# table = data.sheet_by_name('这是一个合同')  # excel表名
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\offer\\报价单.docx'  # 模版位置
# nrows = table.nrows

with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\offer\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  document = MailMerge(template)

  document.merge(
    Project2 = "",
    Item2 = "",
    Price2 = "",
    Brief2 = "",
    LTotal2 = "",
    
    Privider = jdata["报价提供人"],
    Date = jdata["报价日期"],
    StartDate = jdata["报价有效期"][0],
    EndDate = jdata["报价有效期"][1],
    Subtotal = jdata["小计"],
    Total = jdata["总计"],
    Tax = jdata["税率（8%）"],
    
    Project1 = jdata["项目表格"][0]["xiangmu"],
    Item1 = jdata["项目表格"][0]["fenxiang"],
    Price1 = jdata["项目表格"][0]["danjia"],
    Brief1 = jdata["项目表格"][0]["shuoming"],
    LTotal1 = jdata["项目表格"][0]["hangheji"],
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\offer\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS014"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
