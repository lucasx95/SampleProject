import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOneToOne } from './sample-one-to-one.model';
import { SampleOneToOnePopupService } from './sample-one-to-one-popup.service';
import { SampleOneToOneService } from './sample-one-to-one.service';

@Component({
    selector: 'jhi-sample-one-to-one-dialog',
    templateUrl: './sample-one-to-one-dialog.component.html'
})
export class SampleOneToOneDialogComponent implements OnInit {

    sampleOneToOne: SampleOneToOne;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private sampleOneToOneService: SampleOneToOneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sampleOneToOne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sampleOneToOneService.update(this.sampleOneToOne));
        } else {
            this.subscribeToSaveResponse(
                this.sampleOneToOneService.create(this.sampleOneToOne));
        }
    }

    private subscribeToSaveResponse(result: Observable<SampleOneToOne>) {
        result.subscribe((res: SampleOneToOne) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SampleOneToOne) {
        this.eventManager.broadcast({ name: 'sampleOneToOneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sample-one-to-one-popup',
    template: ''
})
export class SampleOneToOnePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleOneToOnePopupService: SampleOneToOnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sampleOneToOnePopupService
                    .open(SampleOneToOneDialogComponent as Component, params['id']);
            } else {
                this.sampleOneToOnePopupService
                    .open(SampleOneToOneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
