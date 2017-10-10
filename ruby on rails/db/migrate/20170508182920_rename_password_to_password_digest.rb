class RenamePasswordToPasswordDigest < ActiveRecord::Migration[5.1]
  def change
    rename_column :users, :password, :password_digest

    remove_column :comments, :timestamp
    remove_column :comments, :commentId
    remove_column :users, :userId
    remove_column :ratings, :ratingId
    remove_column :categories, :categoryId
    remove_column :books, :bookId


  end
end
