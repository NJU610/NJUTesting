from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
from docx2pdf import convert
from mailmerge import MailMerge  # 引用邮件处理模块

def main(argv):
      
  o_name = ""
  i_path = ""
  
  try:
    opts, args = getopt.getopt(argv,'o:i:',["o_name=","i_path="])
  except getopt.GetoptError:
    sys.exit(2)
    
  for opt, arg in opts:
      if opt == "-o":
          o_name = arg
      elif opt == "-i":
          i_path = arg 

  
  template = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS006\\NST－04－JS006－2011－软件测试方案.docx'  # 模版位置
  with open(i_path, 'r', encoding='utf8') as fp:
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
    o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS006\\' #输出地址

    wordname = o_path + o_name + '.docx' 
    document.write(wordname)  # 创建新文件
    convert(wordname, o_path + o_name + '.pdf')
    
if __name__ == '__main__':
    main(sys.argv[1:])
    



      
    
