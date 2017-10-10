class AddUserRightsToUser < ActiveRecord::Migration[5.1]
  def change
    add_column :users, :user_profile, :string, :default => 'user'
  end
end
