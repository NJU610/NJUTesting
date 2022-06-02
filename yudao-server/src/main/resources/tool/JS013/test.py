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
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js013\\NST－04－JS013－2011 - 测试方案评审表.docx'  # 模版位置
# nrows = table.nrows
with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js013\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  
  document = MailMerge(template)

  document.merge(
    SoftwareName = jdata["softName"],
    SoftwareVer = jdata["version"],
    ProjectID = jdata["contractId"],
    TestType = jdata["type"],
    
    State1 = jdata["result"][0]["state"],
    State2 = jdata["result"][1]["state"],
    State3 = jdata["result"][2]["state"],
    State4 = jdata["result"][3]["state"],
    State5 = jdata["result"][4]["state"],
    State6 = jdata["result"][5]["state"],
    State7 = jdata["result"][6]["state"],
    State8 = jdata["result"][7]["state"],
    
    Reason1 = jdata["result"][0]["reason"],
    Reason2 = jdata["result"][1]["reason"],
    Reason3 = jdata["result"][2]["reason"],
    Reason4 = jdata["result"][3]["reason"],
    Reason5 = jdata["result"][4]["reason"],
    Reason6 = jdata["result"][5]["reason"],
    Reason7 = jdata["result"][6]["reason"],
    Reason8 = jdata["result"][7]["reason"],
    
    Advice1 = jdata["advice"][0]["advice"],
    Advice2 = jdata["advice"][1]["advice"],
    Advice3 = jdata["advice"][2]["advice"],
    Advice4 = jdata["advice"][3]["advice"],
    Advice5 = jdata["advice"][4]["advice"],
    
    Sig1 = jdata["advice"][0]["signature"],
    Sig2 = jdata["advice"][1]["signature"],
    Sig3 = jdata["advice"][2]["signature"],
    Sig4 = jdata["advice"][3]["signature"],
    Sig5 = jdata["advice"][4]["signature"],
    
    Date1 = jdata["advice"][0]["date"],
    Date2 = jdata["advice"][1]["date"],
    Date3 = jdata["advice"][2]["date"],
    Date4 = jdata["advice"][3]["date"],
    Date5 = jdata["advice"][4]["date"],
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js013\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS013"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
