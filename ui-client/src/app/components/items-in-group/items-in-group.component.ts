import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ItemsService } from '../../services/items.service';
import { Items } from '../../models/Items';

@Component({
	selector: 'app-items-in-group',
	templateUrl: './items-in-group.component.html',
	styleUrls: ['./items-in-group.component.css']
})
export class ItemsInGroupComponent implements OnInit {
	
	groupId: string;
	items: Items[];
	
	constructor(private itemsService: ItemsService, private router: Router, private route: ActivatedRoute) { }

	ngOnInit() {
		
		this.route.url.subscribe(url=> {
			this.groupId = this.route.snapshot.params['groupId'];
			if (this.groupId) {
				this.getItems(this.groupId);
			}
		});
		
	}

	getItems(groupId) {
		this.itemsService.getByGroupId(groupId).subscribe(items=> this.items = items);
	}

}
