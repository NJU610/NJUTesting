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
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS007\\NST－04－JS004－2011－软件委托测试合同.docx'  # 模版位置
# nrows = table.nrows
with open("C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS007\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  document = MailMerge(template)

  _TestBasis1 = ""
  _TestBasis2 = ""
  _TestBasis3 = ""
  if len(jdata["样品状态"]) <= 1:
      _TestBasis1 = jdata["样品状态"][0]
  if len(jdata["样品状态"]) <= 2:
      _TestBasis2 = jdata["样品状态"][1]
  if len(jdata["样品状态"]) <= 3:
      _TestBasis3 = jdata["样品状态"][2]
      
  document.merge(
    SoftName = jdata["软件名称_1"],
    Version = jdata["版本号_1"],
    Delegation = jdata["委托单位_1"],
    TestType = jdata["测试类别_1"],
    Date = jdata["报告日期_1"],
    Delegation1 = jdata["委托单位"],
    ProjNum = jdata["项目编号"],
    SampleName = jdata["样品名称"],
    SampleVer = jdata["版本/型号"],
    SampleDate = jdata["来样日期"],
    TestType1 = jdata["测试类型"],
    TestBeginDate = jdata["测试时间"][0],
    TestEndTime = jdata["测试时间"][1],
    SampleStatus = jdata["样品状态"],
    
    TestBasis1 = _TestBasis1,
    TestBasis2 = _TestBasis2,
    TestBasis3 = _TestBasis3,
    
    SampleList = jdata["样品清单"],
    TestResult = jdata["测试结论"],
    
    Tester = jdata["主测人"],
    TestDate = jdata["主测_日期"],
    Assesser = jdata["审核人"],
    AssessDate = jdata["审核_日期"],
    Approver = jdata["批准人"],
    ApproveDate = jdata["批准_日期"],
    DelTel = jdata["电话"],
    DelFax = jdata["传真"],
    DelAddr = jdata["地址"],
    DelPost = jdata["邮编"],
    DelCom = jdata["联系人"],
    DelEmail = jdata["E-mail"],
   
    HardType = jdata["硬件类别"],
    HardName = jdata["硬件名称"],
    HardSetting = jdata["配置"],
    HardNum = jdata["数量"],
    
    OS = jdata["软件环境"][0]["name"],
    OSVer = jdata["软件环境"][0]["version"],
    Soft1 = jdata["软件环境"][1]["name"],
    Soft1Ver = jdata["软件环境"][1]["version"],
    Soft2 = "",
    SoftVer = "",
    Soft3 = "",
    Soft3Ver = "",
    Aux = jdata["软件环境"][2]["name"],
    AuxVer = jdata["软件环境"][2]["version"],
    Dev = jdata["软件环境"][3]["name"],
    DevVer = jdata["软件环境"][3]["version"],
    TestedSample = jdata["软件环境"][4]["name"],
    TestedSampleVer = jdata["软件环境"][4]["version"],
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS007\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS004"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
