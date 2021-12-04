Per poter usare questo progetto occore avere questo profile attivo su settings.zml di mave

<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 http://maven.apache.org/xsd/settings-1.2.0.xsd">
  <activeProfiles>
        <activeProfile>alfresco</activeProfile>
    </activeProfiles>
    <profiles>
		<profile>
			<id>alfresco</id>
		  <repositories>
		  
			<repository>
			  <id>alfresco-public</id>
			  <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
			</repository>
		  
		  </repositories>
		</profile>
	</profiles>
</settings>


se c'è un problema di certificato con java occorre registrare nel keytool di java
su windows 
keytool.exe -import -alias alf-pub-repo-cert -keystore 'C:\Program Files\Java\jdk-11.0.5\lib\security\cacerts' -file 'C:\Users\vitoc\Alfresco\certificato.cer'   