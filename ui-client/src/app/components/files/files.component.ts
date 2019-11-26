import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { FilesService } from '../../services/files.service';
import { FilesToDomainMapper } from '../../models/FilesToDomainMapper';
import { SafePipe } from '../../pipes/safe.pipe';

@Component({
	selector: 'app-files',
	templateUrl: './files.component.html',
	styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

	@Input() domainId: String;
	filesForUpload = {files: [], src: []};
	filesToDomainMapper: FilesToDomainMapper = {fileIds: []};
	tableDataIsReady: boolean = false;
	filesUrl: string;
	filesForm: FormGroup;


	constructor(private filesService: FilesService, private formBuilder: FormBuilder) {
		this.filesForm = this.formBuilder.group({
			files: [null, Validators.required]
		});
	}

	ngOnInit() {
		this.addEventListenerToFileInput(this.filesForUpload, this.filesService);
		if (this.domainId) {
			this.getFilesToDomainMapping(this.domainId);
		} else {
			this.filesToDomainMapper = {domainId: null, fileIds: []};
		}
		this.filesUrl = this.filesService.getFilesUrl();
	}

	save() {
		if (!this.domainId) {
			return;
		}

		this.filesService.save(this.domainId).subscribe(response => {
			this.filesService.resetFormData();
			this.filesToDomainMapper = response;
			this.filesForUpload.files.length = 0;
			this.filesForUpload.src.length = 0;
		});
	}

	removeFromLocal({id, index}) {
		if(!confirm('Are you sure?')) {
			return;
		}

		this.filesService.removeLocalFile(this.filesForUpload.files[index]);
		this.filesForUpload.src.splice(index, 1);
		this.filesForUpload.files.splice(index, 1);
	}

	remove({id, index}) {
		if(!confirm('Are you sure?')) {
			return;
		}

		this.filesService.remove({domainId: this.domainId, id: id}).subscribe(() => {
			this.filesToDomainMapper.fileIds.splice(index, 1);
		});	
	};

	private getFilesToDomainMapping(domainId) {
		this.filesService.getByDomainId(domainId).subscribe(filesToDomainMapper => {
			this.filesToDomainMapper = filesToDomainMapper;
			this.tableDataIsReady = true;
		});
	}

	private addEventListenerToFileInput(filesForUpload, filesService) {
		var input = document.getElementById('multipartFile');
		input.addEventListener('change', function(event: any) {
			var files  = event.target.files;
			for (var i = 0; i < files.length; i++) {
				filesForUpload.files.push(files[i]);
				filesForUpload.src.push(URL.createObjectURL(files[i]));
				filesService.updateFormData('files', files[i]);
			}
		});
	};
}
