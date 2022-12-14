## 1.项目概述

目前校内的公寓报修只能通过微信提交表单形式来进行报修，不变于数据的集中分析。因此，在提交表单的基础上，通过增加过往维修记录功能，进行系统的开发。

从维护者和使用者的角度出发，以高效管理、满足使用者为原则，要求本系统满足以下要求：

（1） 统一友好的操作界面，具有良好的用户体验。

（2） 系统管理员可以进行增删改查等操作。

（3） 用户可通过关键字搜索，查看相应的信息。

（4） 提供简单的安全模型，系统管理员的操作需要先登录，输入正确密码才可以进行操作。

（5） 用户可以在后台查看自己的报修订单和水电使用情况。

（6） 设计网站后台，管理网站的各项基本数据。

（7） 系统运行安全稳定，响应及时。



##  2.系统业务流程分析

公寓报修系统主要包括前台报修、后台管理两个个功能模块以及若干子模块。



### 2.1 前台报修管理模块设计

前台报修管理模块包括登录管理、报修管理、查看报修记录、反馈管理四个子模块。

（1）  登录管理:用于验证身份；

（2）  报修管理：用于报修信息的录入；

（3）  查看报修管理：用于学生产看本人的报修记录。

（4）   反馈管理：用于对维修结果的反馈，可以对已反馈的信息进行删除。学生可以将一次报修且维修后的反复性情况、时间等一系列的后续进行反馈评价，以督促宿管处对报修功能的重视和改正

### 2.2 后台管理模块设计

后台管理功能模块包括登录管理、用户管理、楼栋管理、维修记录管理、反馈管理五个子模块。

（1）  登录管理:用于验证身份；

（2）  用户管理：提供对学生信息的维护功能 ，同时还需提供对学生信息批量删除、导入、导出、组合查询功能。

（3）  楼栋管理：提供对房间信息的维护功能，楼栋信息包括楼栋号、房间个数、是否有空调、楼栋性别、楼层数、使用群体、使用状态、备注。系统管理员可以快捷修改楼栋使用状态，可以批量导入、导出、删除楼栋信息。

（4）  维修记录管理：维修记录功能模块依旧只有管理员才能进入并进行操作，其中显示的是具体宿舍楼、宿舍号的学生报修情况和报修时间，系统管理员可以进行增加报修的类型，根据维修的情况进行已修和未修操作。

（5）  反馈管理：管理员可以查看反馈管理模块中显示的学生反馈，对已经实施的反馈进行操作，即删除已经阅读过的留言。

（6）  信息统计：提供统计所有楼栋中已经入住学生的学生数量以及空余床位数量，水电使用情况、维修情况的功能。通过收集过往的维修情况的汇总，给出一定的评估和预测推断。管理员可以随时了解到某个房间的维修次数和安全评估。另外该模块可以让学生和管理员可以查看本月或近一年内的水电使用情况。统计结果以表格的形式展现[1]。

（7）  通知公告管理：系统管理员和宿舍管理员均可以发布通知公告，同时能选择是否将通知信息以电子邮件的形式发送至学生邮箱，可以对发布过的通知信息进行维护操作。学生用户可以查看自己所在楼栋宿舍管理员发布的通知和所有系统管理员发布的通知，可以接收邮件形式的通知信息。
