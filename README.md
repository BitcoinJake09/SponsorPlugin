Simple GUI Sponsor Display Plugin

drop a release jar in your plugins folder or selfcompile the jar

uses SponsorPlugin/config.json 

example config:
[{"config":{"SPONSOR1":{"NAME":"AltQuest Network", "MSG":"Hosting provider"},"SPONSOR2":{"NAME":"DeVault.cc", "MSG":"Crypto Prize"}}}]

![welcome message](https://media.discordapp.net/attachments/419294985419096064/891808814142210128/2021-09-26_18.08.55.png?width=790&height=444)

![SPONSOR1](https://media.discordapp.net/attachments/419294985419096064/891808809343914034/2021-09-26_18.08.57.png?width=790&height=444)

![SPONSOR2](https://media.discordapp.net/attachments/419294985419096064/891808805258657832/2021-09-26_18.09.02.png?width=790&height=444)

![ScoreBoardDisplay](https://media.discordapp.net/attachments/419294985419096064/891815971277389834/2021-09-26_18.37.57.png?width=742&height=444)


to self compile
$git clone https://github.com/BitcoinJake09/SponsorPlugin
$mvn clean compile assembly:single

jar will be in target folder
