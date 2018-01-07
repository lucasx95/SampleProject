import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOneToOne } from './sample-one-to-one.model';
import { SampleOneToOnePopupService } from './sample-one-to-one-popup.service';
import { SampleOneToOneService } from './sample-one-to-one.service';

@Component({
    selector: 'jhi-sample-one-to-one-delete-dialog',
    templateUrl: './sample-one-to-one-delete-dialog.component.html'
})
export class SampleOneToOneDeleteDialogComponent {

    sampleOneToOne: SampleOneToOne;

    constructor(
        private sampleOneToOneService: SampleOneToOneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sampleOneToOneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sampleOneToOneListModification',
                content: 'Deleted an sampleOneToOne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sample-one-to-one-delete-popup',
    template: ''
})
export class SampleOneToOneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleOneToOnePopupService: SampleOneToOnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sampleOneToOnePopupService
                .open(SampleOneToOneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
