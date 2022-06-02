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
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js006\\NST－04－JS006－2011－软件测试方案.docx'  # 模版位置
# nrows = table.nrows
with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js006\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  
  document = MailMerge(template)

  document.merge(
    Label = jdata["label"],
    SysOverview = jdata["systemOverview"],
    DocOverview = jdata["documentOverview"],
    Baseline = jdata["baseLine"],
    File = jdata["quotedFile"],
    HardwareEnv = jdata["hardwareEnvironment"],
    SoftwareEnv = jdata["softwareEnvironment"],
    OtherEnv = jdata["otherEnvironment"],
    Org = jdata["participantOrganization"],
    Person = jdata["participant"],
    OverallDesign = jdata["overallDesign"],
    TestLayer = jdata["testLayer"],
    TestType = jdata["testType"],
    TestCon = jdata["generalTestCondition"],
    Plan = jdata["plannedTests"],
    Testcase = jdata["testCases"],
    Wq1 = jdata["workQuantity1"],
    Wq2 = jdata["workQuantity2"],
    Wq3 = jdata["workQuantity3"],
    Wq4 = jdata["workQuantity4"],
    St1 = jdata["time1"][0],
    St2 = jdata["time2"][0],
    St3 = jdata["time3"][0],
    St4 = jdata["time4"][0],
    En1 = jdata["time1"][1],
    En2 = jdata["time2"][1],
    En3 = jdata["time3"][1],
    En4 = jdata["time4"][1]
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js006\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS006"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
