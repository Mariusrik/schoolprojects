# Be sure to restart your server when you modify this file.

# Configure sensitive parameters which will be filtered from the log file.
Rails.application.config.filter_parameters += [:password_digest]
Rails.application.config.filter_parameters += [:password_salt]
Rails.application.config.filter_parameters += [:password]

