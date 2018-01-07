import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SampleMany } from './sample-many.model';
import { SampleManyPopupService } from './sample-many-popup.service';
import { SampleManyService } from './sample-many.service';

@Component({
    selector: 'jhi-sample-many-delete-dialog',
    templateUrl: './sample-many-delete-dialog.component.html'
})
export class SampleManyDeleteDialogComponent {

    sampleMany: SampleMany;

    constructor(
        private sampleManyService: SampleManyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sampleManyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sampleManyListModification',
                content: 'Deleted an sampleMany'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sample-many-delete-popup',
    template: ''
})
export class SampleManyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleManyPopupService: SampleManyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sampleManyPopupService
                .open(SampleManyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
