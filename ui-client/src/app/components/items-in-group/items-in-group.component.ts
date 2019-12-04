import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ItemsService } from '../../services/items.service';
import { Items } from '../../models/Items';
import { FilesService } from '../../services/files.service';

@Component({
	selector: 'app-items-in-group',
	templateUrl: './items-in-group.component.html',
	styleUrls: ['./items-in-group.component.css']
})
export class ItemsInGroupComponent implements OnInit {
	
	groupId: string;
	items: Items[];
	filesUrl: string;

	constructor(private itemsService: ItemsService, private router: Router, private route: ActivatedRoute,
		private filesService: FilesService) { }

	ngOnInit() {
		
		this.route.url.subscribe(url=> {
			this.groupId = this.route.snapshot.params['groupId'];
			if (this.groupId) {
				this.getItems(this.groupId);
			}
		});

		this.filesUrl = this.filesService.getFilesUrl();
	}

	getItems(groupId) {
		this.itemsService.getByGroupId(groupId).subscribe(items=> this.items = items);
	}

	buyyyy(item: Items) {
		alert('Buy item: ' + item.name);
	}

}
