import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ItemsService } from '../../services/items.service';
import { Items } from '../../models/Items';

@Component({
	selector: 'app-items',
	templateUrl: './items.component.html',
	styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {
	
	items: Items[];
	
	constructor(private itemsService: ItemsService, private router: Router) { }

	ngOnInit() {
		this.itemsService.get().subscribe(items => this.items = items);
	}

	add() {
		this.router.navigate(['/items_add']);
	};

	edit(id) {
		this.router.navigate(['/items_edit', id]);
	};

	remove({id, index}) {
		if(!confirm('Are you sure?')) {
			return;
		}

		this.itemsService.delete(id).subscribe(()=> this.items.splice(index, 1));
	}

}
