# 存储&网络性能测试报告

## 存储性能测试
### 测试工具：IOmeter

### 磁盘：SanDisk SD7SN6S128G


### 测试参数：

maxium disk size:128 sectors

update frequency:5 seconds

run time: 2 minutes

### 测试结果

|Access Specification Name|IOps|MiBps (Binary)|
|---------------|----|------|
|512 B; 100% Read; 0% random|7094.014238|3.463874|
|512 B; 75% Read; 0% random|4545.427113|2.219447|
|512 B; 50% Read; 0% random|4374.282034|2.13588|
|512 B; 25% Read; 0% random|5113.1558|2.496658|
|512 B; 0% Read; 0% random|9354.259447|4.567509|
|4 KiB; 100% Read; 0% random|6965.150742|27.20762|
|4 KiB; 75% Read; 0% random|5612.75836|21.924837|
|4 KiB; 50% Read; 0% random|5754.327805|22.477843|
|4 KiB; 25% Read; 0% random|3212.502801|12.548839|
|4 KiB; 0% Read; 0% random|17102.88563|66.808147|
|16 KiB; 100% Read; 0% random|4572.21915|71.440924|
|16 KiB; 75% Read; 0% random|3719.65284|58.119576|
|16 KiB; 50% Read; 0% random|3712.980608|58.015322|
|16 KiB; 25% Read; 0% random|4959.40142|77.490647|
|16 KiB; 0% Read; 0% random|10902.52316|170.351924|

### 分析

对于该固态硬盘：

1.随机写的IOps以及MiBps要大于随机读

2.完全随机写或随机读的IOps以及MiBps要大于读写混合

3.随着transfer request size的增大，MiBps显著增大，随机读的IOps略有降低，随机写的IOps在4KB时最大


## 网络性能测试

### 测试工具：IOmeter/Dynamo

### 宽带：200M

### 测试参数：

本机ip:192.168.1.26

待测机ip:192.168.99.1

update frequency:5 seconds

run time: 2 minutes

### 测试结果

|Access Specification Name|IOps|MiBps (Binary)|
|---------------|----|------|
|512 B; 100% Read; 0% random|81774.72659|39.929066|
|512 B; 50% Read; 0% random|56907.81127|27.787017|
|512 B; 0% Read; 0% random|48899.03405|23.876481|
|4 KiB; 100% Read; 0% random|76340.45893|298.204918|
|4 KiB; 50% Read; 0% random|40176.80853|156.940658|
|4 KiB; 0% Read; 0% random|35897.09806|140.223039|
|16 KiB; 100% Read; 0% random|22228.68872|347.323261|
|16 KiB; 50% Read; 0% random|17153.06477|268.016637|
|16 KiB; 0% Read; 0% random|15694.77206|245.230813|

### 分析

对于该网络：

1.随机读的IOps以及MiBps较随机写要大，并且随着随机写的比例增大，IOps以及MiBps单调减少

2.随着transfer request size的增大，IOps单调减少，MiBps单调增加
