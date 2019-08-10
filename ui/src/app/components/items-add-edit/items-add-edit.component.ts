import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ItemsService } from '../../services/items.service';
import { Items } from '../../models/Items';
import { GroupsService } from '../../services/groups.service';
import { Groups } from '../../models/Groups';
import { FilesService } from '../../services/files.service';

@Component({
	selector: 'app-items-add-edit',
	templateUrl: './items-add-edit.component.html',
	styleUrls: ['./items-add-edit.component.css']
})
export class ItemsAddEditComponent implements OnInit {

	item: Items = {name: '', groupId: ''};
	itemForm: FormGroup;
	id: string;
	groups: Groups[];

	constructor(private itemsService: ItemsService, private groupsService: GroupsService, private filesService: FilesService, 
		private route:ActivatedRoute, private formBuilder: FormBuilder, private router: Router) {
		this.buildForm(this.item);
	}

	ngOnInit() {
		this.id = this.route.snapshot.params['id'];
		this.groupsService.get().subscribe(groups => this.groups = groups);
		
		if (this.id) {
			this.itemsService.getById(this.id).subscribe(item => {
				this.item = item; 
				this.buildForm(this.item);
			});
		}
	}

	save(item: Items) {
		this.itemsService.save(item).subscribe((response) => this.saveFilesAndGoBack(response.id));
	}

	update(item: Items) {
		item.id = this.id;
		this.itemsService.update(item).subscribe((response) => this.saveFilesAndGoBack(response.id));
	}

	buildForm(item: Items) {
		this.itemForm = this.formBuilder.group({
			'name' : [item.name, Validators.required],
			'groupId' : [item.groupId, Validators.required],
			'price' : [item.price],
			'shortDescription' : [item.shortDescription],
			'description' : [item.description],
			'validate' : ''
		});
	}

	goBack() {
		this.filesService.resetFormData();
		this.router.navigate(['/items']);
	}

	private saveFilesAndGoBack(itemId: string) {
		this.filesService.save(itemId).subscribe(() => {
			this.filesService.resetFormData();
			this.goBack();
		});
	}
}
