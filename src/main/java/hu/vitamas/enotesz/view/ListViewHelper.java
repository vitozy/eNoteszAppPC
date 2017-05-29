/*
 * Copyright (C) 2017 Vincze Tamas Zoltan (www.vitamas.hu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.vitamas.enotesz.view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import hu.vitamas.enotesz.model.ListRecord;

/**
 * I's class help making {@link ListView}.
 * 
 * @author vitozy
 *
 */
public class ListViewHelper {

	/**
	 * Adds icon to the items of a ListVew object.
	 * 
	 * @param listView the listview to concat icon
	 * @param image the icon name in images resources folder without .png extension
	 */
	public static void addIconToItems(ListView<ListRecord> listView, String image) {
		listView.setCellFactory(param -> new ListCell<ListRecord>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(ListRecord rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	imageView.setImage(new Image(ListViewHelper.class.getResource("/images/" + image + ".png").toString()));
                    setText(rec.getTitle());
                    setGraphic(imageView);
                }
            }
        });
	}
}
