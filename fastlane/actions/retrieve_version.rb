module Fastlane
  module Actions
    class RetrieveVersionAction < Action
      def self.run(params)
        version = File.read("app/build.gradle.kts").split("versionName = \"", 2)[1].split("\"")[0]
        UI.message("Found rostrumversion: #{version}")
        return version
      end

      #####################################################
      # @!group Documentation
      #####################################################

      def self.description
        'Retrieving current rostrumversion from app/build.gradle.kts'
      end

      def self.is_supported?(platform)
        true
      end
    end
  end
end
