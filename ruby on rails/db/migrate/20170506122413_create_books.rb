class CreateBooks < ActiveRecord::Migration[5.1]
  def change
    create_table :books do |t|
      t.integer :bookId
      t.string :title
      t.string :description
      t.string :image
      t.timestamps
    end
  end
end
